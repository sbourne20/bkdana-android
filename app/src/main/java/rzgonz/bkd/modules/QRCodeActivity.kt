package rzgonz.bkd.modules

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_qrcode.*
import rzgonz.bkd.R
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.helper.SharedPreferenceService

class QRCodeActivity : DIBaseActivity() {


    override fun initLayout(): Int {
        return R.layout.activity_qrcode
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("QR CODE")
        val memberId =SharedPreferenceService(applicationContext).getString("MEMBER_ID","")
        val multiFormatWriter =  MultiFormatWriter();
        try {
            val bitMatrix = multiFormatWriter.encode(memberId, BarcodeFormat.QR_CODE,300,300);
            val barcodeEncoder =  BarcodeEncoder();
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            imgQR.setImageBitmap(bitmap)
        } catch ( e: WriterException) {
            e.printStackTrace()
        }
        tvMemberId.setText(memberId)
    }

    override fun inject() {

    }

    override fun onAttachView() {

    }

    override fun onDetachView() {

    }
}
