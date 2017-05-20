package com.whoshell.service.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.whoshell.util.ConfigService;
import com.whoshell.util.UUIDGenerate;

/**
 * QrCodeService ： 生成二维码业务类
 * @author XianSky
 *
 */
@Service("qrCodeService")
public class QrCodeService {


	/**
	 * 日志对象
	 */
	Log log = LogFactory.getLog(QrCodeService.class);
	
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xFFFFFFFF;


	/**
	 * generateArticleQrCode : 生成平台文章对应的临时二维码
	 * 当调用获取带参数二维码得到ticket和url后，就可以将该url写入到我们自定义二维码中，这样扫码就可以正常调用微信接口。
	 * @param articleId
	 * @param ntype
	 */
	public String generateArticleQrCode(String articleId,String url,Integer ntype) {
		String filePostfix="png";
		String sTempPath = System.getProperty("user.dir");
		String sTemppath2 = sTempPath.substring(0, sTempPath.indexOf("bin"));
		//指定图片生成的路径
		String fileLocation = ConfigService.getValue("QRCODELOCATION", "qrcodeLocation");
		String sPath = sTemppath2+fileLocation;
		//二维码图片名称
		String qrcodeName = new UUIDGenerate().genericIdentity() + "." + filePostfix;
		String sGeneratePath = sPath + qrcodeName;
		log.info("生成的文章二维码路径>>" + sGeneratePath);
		File file = new File(sGeneratePath);
		File files = new File(sPath);
		if(!files.exists()){
			files.mkdir();
		}
		
		log.info(">>>生成二维码信息：" + url.toString());
		try {
			encode(new String(url.toString().getBytes("UTF-8"),"ISO-8859-1"), file,filePostfix, BarcodeFormat.QR_CODE, 430, 165, null);
			return qrcodeName;
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	/**
	 *  生成QRCode二维码<br> 
	 *  在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的<br>
	 *  static final String DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";<br>
	 *  修改为UTF-8，否则中文编译后解析不了<br>
	 * @param contents 二维码的内容
	 * @param file 二维码保存的路径，如：C://test_QR_CODE.png
	 * @param filePostfix 生成二维码图片的格式：png,jpeg,gif等格式
	 * @param format qrcode码的生成格式
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param hints
	 */
	public void encode(String contents, File file,String filePostfix, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height);
			writeToFile(bitMatrix, filePostfix, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成二维码图片<br>
	 * 
	 * @param matrix
	 * @param format
	 *            图片格式
	 * @param file
	 *            生成二维码图片位置
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		ImageIO.write(image, format, file);
	}

	/**
	 * 生成二维码内容<br>
	 * 
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
			}
		}
		return image;
	}
}
