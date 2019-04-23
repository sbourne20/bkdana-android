package rzgonz.bkd.services.Firebase.Notification

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import android.app.NotificationManager
import android.R
import android.content.Context
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import rzgonz.bkd.modules.home.gocek
import android.support.v4.content.LocalBroadcastManager
import rzgonz.bkd.modules.transaksi.TransaksiPresenter
import javax.inject.Inject


const val TAG = "MyToken"

class NotificationService : FirebaseMessagingService() {


    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        Log.d(TAG, "Refreshed Token: " + p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        Log.d(TAG, "From: ${remoteMessage?.from}")

        // Check if message contains a data payload.
        remoteMessage?.data?.isNotEmpty()?.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            val title = remoteMessage.data!!.get("title") // ambil judul notifikasi
            val msg   = remoteMessage.data!!.get("body") // ambil pesan notifikasi
            val action = remoteMessage.data !!.get("click_action") // ambil data click action
            if(action.equals("SOMEACTIVITY")){
                gocek = "transaksi"
            } else {
                //do nothing
            }

            sendNotification(action,title,msg)
        }


    }

    private fun sendNotification(Act: String?, title: String?, msg: String?){
        var intent = Intent()

        if ( Act.equals("SOMEACTIVITY")){
            intent = Intent(Act)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.sym_def_app_icon)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 , notificationBuilder.build())
    }
}
