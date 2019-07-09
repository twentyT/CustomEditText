package com.example.danmaohua.socketproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.danmaohua.socketproject.tool.NumberRunningTextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final String SERVER_IP = "47.98.202.4";
    private final int SERVER_PORT = 10001;
    @BindView(R.id.tv_money)
    NumberRunningTextView tvMoney;
    private Thread mConnectThread;
    private Socket mSocket;// socket连接对象
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private Parser parser = new Parser();
    private boolean close = false; // 关闭连接标志位，true表示关闭，false表示连接
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;
    //数据库中的总页数
    private int totalPage;
    //每页包含的可视条目
    private int ItemPage = 10;
    //当前页数初始为1
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        connect();
        new mThread().start();
        sqLiteHelper = new SQLiteHelper(this);
        mCursor = sqLiteHelper.queryAll();
        totoalContent = mCursor.getCount();
        if (myThread == null) {
            myThread = new myThread();
            myThread.start();
        } else {
            myThread.start();
        }
        tvMoney.setContent("90.00");
        Log.d("FE=", (byte) 0XFE + "");
        Log.d("00=", (byte) 00 + "");
        Log.d("0X00=", (byte) 0x00 + "");
        Log.d("01=", (byte) 01 + "");
        Log.d("0x01=", (byte) 0x01 + "");
        Log.d("1=", (byte) 1 + "");
        Log.d("0x1=", (byte) 0x1 + "");
        Log.d("02=", (byte) 02 + "");
        Log.d("0x02=", (byte) 0x02 + "");
        Log.d("90=", (byte) 90 + "");
        Log.d("0x90=", (byte) 0x90 + "");
        Log.d("81=", (byte) 81 + "");
        Log.d("0x81=", (byte) 0x81 + "");
        Log.d("78=", (byte) 78 + "");
        Log.d("0x80=", (byte) 0x80 + "");
    }

    public void connect() {
        if (mConnectThread == null) {
            mConnectThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SocketAddress address = new InetSocketAddress(SERVER_IP, SERVER_PORT);
                        mSocket = new Socket();
                        try {
                            mSocket.connect(address, 5000);
                            mSocket.setSoTimeout(0);
                        } catch (IOException e) {
                            Log.e("哈哈", "连接超时！");
                            return;
                        }
                        if (mSocket.isConnected()) {
                            Log.e("哈哈", "链接成功！");
                            inputStream = mSocket.getInputStream();
                            outputStream = mSocket.getOutputStream();
                            // init
                            Packet packet = new Packet(0);
                            packet.from = 18;
                            packet.to = 18;
                            packet.len = 0;
                            packet.fcs = packet.generateCRC();
                            byte[] bytes = new byte[7];
                            packet.toBytes(bytes);
                            outputStream.write(bytes, 0, 7);
                        }
                        while (!mSocket.isClosed()) {
                            //接收服务器的数据
                            Packet packet = null;
                            do {
                                packet = parser.message_parse_char((byte) inputStream.read());
                            } while (packet == null);
                            byte[] bytes = new byte[packet.len + 7];  // 4 + 3
                            packet.toBytes(bytes);
                            System.out.println(Arrays.toString(bytes));
                            Log.e("哈哈", "返回的数据" + Arrays.toString(bytes));

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        safeClose(inputStream);
                        safeClose(outputStream);
                    }
                }
            });
            Log.e("哈哈", "重新连接！");
            mConnectThread.start();
        }
    }

    /**
     * 安全关闭PrintWriter
     *
     * @param os
     */
    private static final void safeClose(OutputStream os) {
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全关闭BufferedReader
     *
     * @param is
     */
    private static void safeClose(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ignored) {
            }
        }
    }

    private Boolean a = true;

    //发送心跳包，监测断网。当心跳包发送成功时则表示网络畅通，失败时则从新连接调用connect（），10分钟检查一次；
    private class mThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (a == true) {
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (isServerClose(mSocket)) {
                        mConnectThread = null;
                        connect();
                        Log.e("哈哈", "发现断网！");
                    } else {
                        Log.e("哈哈", "有网状态");
                    }
                }
            }
        }
    }

    /**
     * 判断是否断开连接，断开返回true,没有返回false
     *
     * @param socket
     * @return
     */
    public Boolean isServerClose(Socket socket) {
        try {
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        } catch (Exception se) {
            return true;
        }
    }

    /**
     * 发送数据，发送失败返回false,发送成功返回true
     *
     * @param csocket
     * @param message
     * @return
     */
    public Boolean Send(Socket csocket, String message) {
        try {
            PrintWriter out = new PrintWriter(mSocket.getOutputStream(), true);
            out.println(message);
            return true;
        } catch (Exception se) {
            se.printStackTrace();
            return false;
        }
    }

    public void addData(View view) {
        for (int i = 0; i < 100; i++) {
            sqLiteHelper.insert(i + "号", i + "号", i + "号");
        }
    }

    public void deleteData(View view) {

    }

    public void onDraws(View view) {
        startActivity(new Intent(this,ActivirtCustomView.class));
    }

    public void modifyData(View view) {
    }

    private Cursor mCursor;

    public void findData(View view) {
        ++currentPage;
        if (myThread == null) {
            myThread = new myThread();
            myThread.start();
        } else {
            myThread.run();
        }
    }

    public void luckyPan(View view) {
        startActivity(new Intent(this, Turntable.class));
    }

    private int totoalContent = 0;
    private Thread myThread;

    class myThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (mCursor != null) {
                while (mCursor.moveToNext()) {
                    int _id = mCursor.getInt(0);
                    String name = mCursor.getString(1);
                    String status = mCursor.getString(2);
                    String time = mCursor.getString(3);
                    if (_id == (totoalContent - ItemPage * currentPage)) {
                        break;
                    }
                    Log.e("数据包", _id + "数据库拿数据" + name);
                }
            }
            Log.e("总数据:", totoalContent + "条数据");
        }
    }
    //获取list的cursor集合

    //加载下一页的方法
//    private void loadNextPage(int currentPage) {
//        Cursor cursor = db.rawQuery("select * from Book limit ?,?", new String[]{String.valueOf(ItemPage * currentPage), String.valueOf(ItemPage)});
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                String _id = cursor.getString(0);
//                String name = cursor.getString(1);
//                String status = cursor.getString(2);
//                String time = cursor.getString(3);
//                Log.e("数据包", "数据库拿数据" + name);
//            }
//        }
//    }
}
