package com.william.launchmode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.william.launchmode.launch_mode.SingleInstanceActivity
import com.william.launchmode.launch_mode.SingleTaskActivity
import com.william.launchmode.launch_mode.SingleTopActivity
import com.william.launchmode.launch_mode.StandardActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * @author WeiYi Yu
 * @date 2019-06-28
 */
open class BaseActivity : AppCompatActivity() {

    /**
     * Note:
     * Launch modes which defined in Manifest can be overridden by Intent flags
     */
    enum class LaunchMode {
        STANDARD, SINGLETOP, SINGLETASK, SINGLEINSTANCE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btnStandard.setOnClickListener {
            startActivity(LaunchMode.STANDARD)
        }

        btnSingleTop.setOnClickListener {
            startActivity(LaunchMode.SINGLETOP)

        }

        btnSingleTask.setOnClickListener {
            startActivity(LaunchMode.SINGLETASK)

        }

        btnSingleInstance.setOnClickListener {
            startActivity(LaunchMode.SINGLEINSTANCE)
        }

        btnFlagClearTop.setOnClickListener {
            startActivity(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Toast.makeText(this, "onNewIntent called", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        title = "${javaClass.simpleName.replace("Activity", "")}(Task:${this.taskId}, Code:${hashCode()})"
    }

    private fun Context.startActivity(launchMode: LaunchMode) {
        val clazz = when (launchMode) {
            LaunchMode.STANDARD -> StandardActivity::class.java
            LaunchMode.SINGLETOP -> SingleTopActivity::class.java
            LaunchMode.SINGLETASK -> SingleTaskActivity::class.java
            LaunchMode.SINGLEINSTANCE -> SingleInstanceActivity::class.java
        }
        startActivity(Intent(this, clazz))
    }

    private fun Context.startActivity(flag: Int) {
//        val clazz = when (flag) {
//            Intent.FLAG_ACTIVITY_NEW_TASK -> StandardActivity::class.java
//            Intent.FLAG_ACTIVITY_CLEAR_TOP -> SingleTopActivity::class.java
//            else -> null
//        }

        finish()
        val intent = Intent(this, StandardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}