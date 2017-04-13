package com.practice.li.rollingdesign.ui.shots.detail.comment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.utils.UIUtils;


/**
 * Created by Lazxy on 2017/4/1.
 */

public class CommentEditDialog extends AppCompatDialog {

    private EditText commentEdit;
    private TextView commentCommit;

    private boolean isCommit=false;

    public CommentEditDialog(@NonNull Context context, DialogInterface.OnCancelListener cancelListener) {
        super(context,R.style.noPaddingDialog);
        setOnCancelListener(cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment_edit);
        initView();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.i("TAG","on start");
        /*每次出现Dialog的时候会执行一次，但onCreate只执行一次*/
        commentEdit.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UIUtils.showInputMethod(getContext());
            }
        },100);

    }

    public void initView(){
        Window dialogWindow=getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp=dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = (int)getContext().getResources().getDimension(R.dimen.comment_edit_height);
        dialogWindow.setAttributes(lp);
        commentEdit=(EditText)findViewById(R.id.et_comment_edit);
        commentCommit=(TextView)findViewById(R.id.tv_comment_commit);
    }

    public void initListener(){
        commentCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(commentEdit.getText())){
                    UIUtils.showSimpleAlertDialog(getContext(), null, "确定提交该评论吗？", "提交", "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isCommit=true;
                            cancel();
                        }
                    },null);
                }
            }
        });
    }

    public boolean isCommit(){
        return this.isCommit;
    }

    public void clearCommitStatus(){
        this.isCommit=false;
        commentEdit.setText("");
    }

    public String getCommentContent(){
        return commentEdit.getText().toString();
    }

    public void setCommentContent(String content){
        commentEdit.setText(content);
    }


}
