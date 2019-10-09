package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ComponentName componentName;
    JobInfo.Builder builder;
    int jobId = 123;
    JobInfo jobInfo;
    JobScheduler jobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitialBackGroundWork();
    }

    private void InitialBackGroundWork() {
        componentName = new ComponentName(this, ExampleJobService.class);

        builder = new JobInfo.Builder(jobId, componentName);
        builder.setRequiresCharging(false);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        builder.setPersisted(true);
        builder.setPeriodic(15*60*1000);

        jobInfo = builder.build();

        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

    }

    public void cancelJob(View view) {
        jobScheduler.cancel(jobId);
        Log.d(TAG,"Job canceled");

    }

    public void scheduleJob(View view) {
        int ResultCode = jobScheduler.schedule(jobInfo);

        if(ResultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job Scheduled");
        }else if(ResultCode == JobScheduler.RESULT_FAILURE){
            Log.d(TAG, "Job Scheduled Fail");
        }else{
            Log.d(TAG, "Job Scheduled Exception error");
        }
    }
}
