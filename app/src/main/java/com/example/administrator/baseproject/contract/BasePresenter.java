package com.example.administrator.baseproject.contract;

/**
 * Created by Administrator on 2017/9/9.
 * 作为一个presenter应该涉及到的大的方面
 */

public interface BasePresenter {

    /**
     * 数据的输入：
     * 可能是Intent、网络、本地数据或者是用户输入
     */
    interface DataInput{

        void fromIntent();

        void fromNetwork();

        void fromLocal();

        void fromUserInput();
    }

    /**
     * 数据的处理：
     * 数据的处理，处理时应该与用户有交互
     */
    interface DataHandle{

        void handle();

        void interact();
    }

    /**
     * 数据的存储：
     * 网络的上传、本地的存储和传给其他组件
     */
    interface DataStore{

        void upload();

        void store();

        void sendToOthers();
    }

    /**
     * 当数据处理完之后：
     * 需要告知用户和其他组件
     */
    interface ResultInform{

        void informToUser();

        void informToOthers();
    }
}
