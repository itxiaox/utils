package com.itxiaox.android.xutils.bitmap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.itxiaox.android.xutils.ui.DisplayUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 *图片工具类
 *@author xiaoxiao
 *created at 2016/5/4 0004 上午 11:00
 */

public class BitmapUtil {

	/**
	 * 根据图片id获取图片并压缩返回bitmap用于显示
	 *@author xiaoxiao
	 * created at 2016/5/4 0004 上午 11:01
	 * @param context 上下文对象
	 * @param id 图片id
	 * @param width 压缩后的图片宽度
	 * @param height 压缩后的图片高度
	 */

	public static Bitmap getSmallBitmap(Context context, int id,int width,int height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), id, options);
		// 计算 缩略图大小为原始图片大小的几分之一 inSampleSize:缩略图大小为原始图片大小的几分之一
		options.inSampleSize = calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(context.getResources(), id, options);
	}

	/**
	 * 根据路径获取图片并压缩返回bitmap用于显示,自定义图片大小
	 *
	 * @param filePath 文件路径
	 * @param width    返回图片宽度
	 * @param height   返回图片高度
	 * @return
	 */

	public static Bitmap getSmallBitmap(String filePath, int width, int height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// 计算 缩略图大小为原始图片大小的几分之一 inSampleSize:缩略图大小为原始图片大小的几分之一
		options.inSampleSize = calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 根据路径获取图片并压缩返回bitmap用于显示
	 * 指定图片大小为 480X800
	 *
	 * @param filePath 文件大小
	 * 固定大小：480 x 640
	 * @return
	 */

	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// 计算 缩略图大小为原始图片大小的几分之一 inSampleSize:缩略图大小为原始图片大小的几分之一
		options.inSampleSize = calculateInSampleSize(options, 480, 640);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	public static Bitmap getSizeBitmap(String filePath,int width,int height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// 计算 缩略图大小为原始图片大小的几分之一 inSampleSize:缩略图大小为原始图片大小的几分之一
		options.inSampleSize = calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 根据图片id获取图片并压缩返回bitmap用于显示
	 * 指定图片大小为 480X800
	 *
	 * @param context 上下文对象
	 * @param id      ResId
	 * @return
	 */

	public static Bitmap getSmallBitmap(Context context, int id) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), id, options);
		// 计算 缩略图大小为原始图片大小的几分之一 inSampleSize:缩略图大小为原始图片大小的几分之一
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeResource(context.getResources(), id, options);
	}

	/**
	 * 获取缩略图， 利用TumbnailUtils实现的，底层是通过Matrix来实现的
	 * @param bitmap 要处理的图像bitmap
	 * @param imageWidth 缩略图的宽
	 * @param imageHeight 缩略图的高
	 * @return 返回缩略图
	 */
	public static Bitmap getThumbnail(Bitmap bitmap,int imageWidth,int imageHeight){
		return ThumbnailUtils.extractThumbnail(bitmap,imageWidth,imageHeight);
	}

	/**
	 *  获取Video缩略图，大小为96x96 利用TumbnailUtils实现的，底层是通过Matrix来实现的
	 * @param filePath
	 * @return 返回缩略图
	 */
	public static Bitmap getVideoThumbnail(String filePath){
		return ThumbnailUtils.createVideoThumbnail(filePath,
				MediaStore.Images.Thumbnails.MICRO_KIND);// MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图
	 * 此方法有两点好处：
	 * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
	 * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
	 * 用这个工具生成的图像不会被拉伸。
	 *
	 * @param imagePath 图像的路径
	 * @param width     指定输出图像的宽度
	 * @param height    指定输出图像的高度
	 * @return 生成的缩略图
	 */
	public Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 获取视频的缩略图
	 * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 *
	 * @param videoPath 视频的路径
	 * @param width     指定输出视频缩略图的宽度
	 * @param height    指定输出视频缩略图的高度度
	 * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图
	 */
	public Bitmap getVideoThumbnail(String videoPath, int width, int height,
									int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		System.out.println("w" + bitmap.getWidth());
		System.out.println("h" + bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * Bitmap -> byte[]
	 * 移动平台图像处理，需要将图像传给native处理，如何传递？将bitmap转换成一个 byte[] 方便传递也方便cpp代码直接处理图像内容。
	 * @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
	 * API>17
	 * @param bitmap
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
	public static byte[] bitmap2ByteArray(Bitmap bitmap) {
		ByteBuffer buf = ByteBuffer.allocate(bitmap.getByteCount());
		bitmap.copyPixelsFromBuffer(buf);
		return buf.array();
	}

	/**
	 * 获取bitmap转化为byte[]
	 *
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToByteArray(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		} else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] datas = baos.toByteArray();
			try {
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return datas;
		}
	}

	/**
	 * Bitmap转换成byte[]
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * base64数据转byte[]
	 * @param base64Data
	 */
	public static byte[] base64ToByteArray(String base64Data){
		byte[] data;
		if (TextUtils.isEmpty(base64Data)) {
			return null;
		} else if (base64Data.startsWith("data:image")) {
			data = Base64.decode(base64Data.split(",")[1], Base64.DEFAULT);
		} else {
			data = Base64.decode(base64Data, Base64.DEFAULT);
		}
		return data;
	}

	/**
	 * 根据压缩图片到固定的大小,因为会进行多次压缩可能会比较耗时，建议在异步线程调用
	 * 压缩Bitmap直接根据长宽比进行调用
	 * createScaledBitmap(@NonNull Bitmap src, int dstWidth, int dstHeight, boolean filter)
	 * 方法进行缩放，只能保证长宽不能保证质量。
	 * @param bitmap    原始图片
	 * @param maxSize   压缩后的大小
	 * @param needRecycle   是否需要回收被压的图片
	 * @return
	 */
	public static byte[] compressBitmap(Bitmap bitmap, double maxSize, boolean needRecycle) {
		if (bitmap == null) {
			return null;
		} else {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			//计算等比缩放
			double x = Math.sqrt(maxSize / (width * height));
			Bitmap tmp = Bitmap.createScaledBitmap(bitmap, (int) Math.floor(width * x), (int) Math.floor(height * x), true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int options = 100;
			//生产byte[]
			tmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
			//判断byte[]与上线存储空间的大小
			if (baos.toByteArray().length > maxSize) {
				//根据内存大小的比例，进行质量的压缩
				options = (int) Math.ceil((maxSize / baos.toByteArray().length) * 100);
				baos.reset();
				tmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
				//循环压缩
				while (baos.toByteArray().length > maxSize) {
					baos.reset();
					options -= 1.5;
					tmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
				}
				recycle(tmp);
				if (needRecycle) {
					recycle(bitmap);
				}
			}
			byte[] data = baos.toByteArray();
			try {
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		}
	}

	/**
	 *
	 * @param bytes bitmap的RGBA数据，生成bitmap
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	public static Bitmap byteArray2Bitmap(byte[] bytes,int imageWidth,int imageHeight){

//		Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
		/**
		 * 来根据ByteArray直接得到bitmap，但是我使用中出现问题。想想其实很简单，就这么一行语句，
		 * 一个bitmap对象必须的参数都没有设置，怎么就可能得到正常的bitmap对象？
		 * 这个方法生效的前提是，提供的bitmapdata是包含了图像参数的，而非简单的RGBA数据。
		 * 而我现在得到的仅仅是RGBA数据，所以需要先得到一个bitmap实例，
		 * 再往里填数据。需要将得到的array再变回buffer，使用buffer的 wrap 方法,包装数组得到buffer.
		 */
		// use Bitmap.Config.ARGB_8888 instead of type is OK
		Bitmap stitchBmp = Bitmap.createBitmap(imageWidth, imageHeight, Config.ARGB_8888);
		stitchBmp.copyPixelsFromBuffer(ByteBuffer.wrap(bytes));
		return stitchBmp;
	}

	/**
	 * 回收Bitmap
	 * @param thumbBmp  需要被回收的bitmap
	 */
	public static void recycle(Bitmap thumbBmp) {
		if (thumbBmp != null && !thumbBmp.isRecycled()) {
			thumbBmp.recycle();
		}
	}

	/**
	 * 将bitmap转化成Base64字符串
	 * Base64文件上传
	 * @param bitmap 图片bitmap
	 * @return
	 */
	public static String bitmapToString(Bitmap bitmap) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}
	/**
	 * 以最省内存的方式读取本地资源的图片，但用了decodeStream后图片将不会自动适应机器分辨率，需要多套图片
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
	/**
	 * 将文件转化成Base64字符串
	 * Base64.URL_SAFE|Base64.NO_WRAP|Base64.NO_PADDING,过滤传到服务器会有特殊字符，
	 * 造成签名失败
	 * @param filePath 图片路径
	 * @return
	 */
	public static String bitmapToString(String filePath,int width,int height) {
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//		Bitmap bm = sizeCompress2(bitmap,width,height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.URL_SAFE|Base64.NO_WRAP|Base64.NO_PADDING);
	}

	/**
	 * 将Base64字符串转化成Bitmap
	 * 
	 * @param string
	 * @return bitmap
	 */
	public static Bitmap stringtoBitmap(String string) {

		Bitmap bitmap = null;
		try {

			byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

		}
		return inSampleSize;

	}


	/**
	 * 根据路径删除图片
	 * 
	 * @param path 文件路径
	 */
	public static void deleteTempFile(String path) {
		if (!TextUtils.isEmpty(path)) {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
		
	}


	/**
	 * 计算合适的inSampleSize
	 */
	public static int computeImageSampleSize(ImageSize srcSize, ImageSize targetSize, ImageView imageView) {
		final int srcWidth = srcSize.width;
		final int srcHeight = srcSize.height;
		final int targetWidth = targetSize.width;
		final int targetHeight = targetSize.height;

		int scale = 1;

		if (imageView == null)
		{
			scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // max
		} else
		{
			switch (imageView.getScaleType())
			{
				case FIT_CENTER:
				case FIT_XY:
				case FIT_START:
				case FIT_END:
				case CENTER_INSIDE:
					scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // max
					break;
				case CENTER:
				case CENTER_CROP:
				case MATRIX:
					scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // min
					break;
				default:
					scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // max
					break;
			}
		}

		if (scale < 1)
		{
			scale = 1;
		}

		return scale;
	}
	/**
	 * 添加图片/视频到图库, 2019/11/27 test by pocus project
	 * @param path 图片/视频地址
	 */
	public static void addPicture2Gallery(Context context, String path) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}


	/**
	 * 添加图片（Bitmap）到图库，并通知图库更新
	 * 暂未测试
	 * @param name
	 * @param bmp
	 * @return
	 */
	public String saveImage2Gallery(Context context,String name, Bitmap bmp) {
		File appDir = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator);
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = name + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			try {
				MediaStore.Images.Media.insertImage(context.getContentResolver(),
						file.getAbsolutePath(), fileName, null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// 最后通知图库更新
			Uri localUri = Uri.fromFile(file);
			Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
			context.sendBroadcast(localIntent);
			return file.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveVideo2Gallery(Context context,File file) {
		//是否添加到相册
		ContentResolver localContentResolver = context.getContentResolver();
		ContentValues localContentValues = getVideoContentValues(context, file, System.currentTimeMillis());
		Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
	}

	private static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("title", paramFile.getName());
		localContentValues.put("_display_name", paramFile.getName());
		localContentValues.put("mime_type", "video/3gp");
		localContentValues.put("datetaken", Long.valueOf(paramLong));
		localContentValues.put("date_modified", Long.valueOf(paramLong));
		localContentValues.put("date_added", Long.valueOf(paramLong));
		localContentValues.put("_data", paramFile.getAbsolutePath());
		localContentValues.put("_size", Long.valueOf(paramFile.length()));
		return localContentValues;
	}
	/**
	 * 截屏
	 * @param activity 当前activity
	 * @param filePath 截图保存路径
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Bitmap takeScreenShot(Activity activity,String filePath) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay().getHeight();
		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
		b = getSmallBitmap(filePath);
		view.destroyDrawingCache();
		return b;
	}

	/**
	 * 方形图转成圆形图
	 * 重新绘制
	 * @param bitmap 转换前的方图
	 * @return 转换后的原图
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth() >> 1;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 获取图片大小
	 * @param bitmap
	 * @return
	 */
	public static long getBitmapsize(Bitmap bitmap) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight();

	}
	/**
	 * 获取拍照后图片的旋转角度
	 * @param filePath 图片路径
	 * @return
	 */
	public static int getPictureDegree(String filePath) {
        int degree  = 0;    
        try {    
                ExifInterface exifInterface = new ExifInterface(filePath);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {    
                case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;    
                        break;    
                case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;    
                        break;    
                case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;    
                        break;    
                }    
        } catch (IOException e) {
                e.printStackTrace();    
        }    
        return degree;    
    }
	/**
	 * 旋转图片
	 * @param bitmap 要选择的图片
	 * @param rotate 选装的角度
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int rotate){
        if(bitmap == null)  
            return null ;  
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();  
        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);  
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

	/**
	 *将Bitmap转换成InputStream
	 */

	public static InputStream bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	/**
	 * 将InputStream转换成Bitmap
	 */
	public static Bitmap inputStream2Bitmap(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

	/**
	 *Drawable转换成InputStream
	 */
	public static InputStream irawable2InputStream(Drawable d) {
		Bitmap bitmap = drawable2Bitmap(d);
		return  bitmap2InputStream(bitmap,100);
	}

	/**
	 *InputStream转换成Drawable
	 */
	public static Drawable inputStream2Drawable(InputStream is) {
		Bitmap bitmap = inputStream2Bitmap(is);
		return bitmap2Drawable(bitmap);
	}

	/**
	 * Drawable转换成byte[]
	 */
	public static byte[] drawable2Bytes(Drawable d) {
		Bitmap bitmap = drawable2Bitmap(d);
		return bitmap2Bytes(bitmap);
	}

	/**
	 * byte[]转换成Drawable
	 */

	public static Drawable bytes2Drawable(byte[] b) {
		Bitmap bitmap = bytes2Bitmap(b);
		return bitmap2Drawable(bitmap);
	}


	/**
	 * byte[]转换成Bitmap
	 */
	public static Bitmap bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return null;
	}

	/**
	 * Drawable转换成Bitmap
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
								: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Bitmap转换成Drawable
	 */
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd;
		return d;
	}

	/**
	 * 将一个view转化为bitmap,相当于对view进行截屏
	 * @param view
	 * @return
     */
	public static Bitmap convertViewToBitmap(View view){
		view.measure(View.MeasureSpec.makeMeasureSpec(100,
				View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(100,
						View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	/**
	 * 根据InputStream获取图片实际的宽度和高度
	 *
	 * @param imageStream
	 * @return
	 */
	public static ImageSize getImageSize(InputStream imageStream)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(imageStream, null, options);
		return new ImageSize(options.outWidth, options.outHeight);
	}

	public static class ImageSize
	{
		int width;
		int height;

		public ImageSize()
		{
		}

		public ImageSize(int width, int height)
		{
			this.width = width;
			this.height = height;
		}

		@Override
		public String toString()
		{
			return "ImageSize{" +
					"width=" + width +
					", height=" + height +
					'}';
		}
	}

	/**
	 * 根据ImageView获适当的压缩的宽和高
	 *
	 * @param view
	 * @return
	 */
	public static ImageSize getImageViewSize(View view)
	{

		ImageSize imageSize = new ImageSize();

		imageSize.width = getExpectWidth(view);
		imageSize.height = getExpectHeight(view);

		return imageSize;
	}

	/**
	 * 根据view获得期望的高度
	 *
	 * @param view
	 * @return
	 */
	private static int getExpectHeight(View view)
	{

		int height = 0;
		if (view == null) return 0;

		final ViewGroup.LayoutParams params = view.getLayoutParams();
		//如果是WRAP_CONTENT，此时图片还没加载，getWidth根本无效
		if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT)
		{
			height = view.getWidth(); // 获得实际的宽度
		}
		if (height <= 0 && params != null)
		{
			height = params.height; // 获得布局文件中的声明的宽度
		}

		if (height <= 0)
		{
			height = getImageViewFieldValue(view, "mMaxHeight");// 获得设置的最大的宽度
		}

		//如果宽度还是没有获取到，憋大招，使用屏幕的宽度
		if (height <= 0)
		{
			DisplayMetrics displayMetrics = view.getContext().getResources()
					.getDisplayMetrics();
			height = displayMetrics.heightPixels;
		}

		return height;
	}

	/**
	 * 根据view获得期望的宽度
	 *
	 * @param view
	 * @return
	 */
	private static int getExpectWidth(View view)
	{
		int width = 0;
		if (view == null) return 0;

		final ViewGroup.LayoutParams params = view.getLayoutParams();
		//如果是WRAP_CONTENT，此时图片还没加载，getWidth根本无效
		if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT)
		{
			width = view.getWidth(); // 获得实际的宽度
		}
		if (width <= 0 && params != null)
		{
			width = params.width; // 获得布局文件中的声明的宽度
		}

		if (width <= 0)

		{
			width = getImageViewFieldValue(view, "mMaxWidth");// 获得设置的最大的宽度
		}
		//如果宽度还是没有获取到，憋大招，使用屏幕的宽度
		if (width <= 0)

		{
			DisplayMetrics displayMetrics = view.getContext().getResources()
					.getDisplayMetrics();
			width = displayMetrics.widthPixels;
		}

		return width;
	}

	/**
	 * 通过反射获取imageview的某个属性值
	 *
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static int getImageViewFieldValue(Object object, String fieldName) {
		int value = 0;
		try {
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			int fieldValue = field.getInt(object);
			if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
				value = fieldValue;
			}
		} catch (Exception e) {
		}
		return value;

	}

	/**
	 *将图片压缩到指定文件大小
	 * @param context 上下文对象
	 * @param srcPath 源文件路径
	 * @param maxSize 压缩到的文件大小 ，单位M
	 * @return 返回文件保存路径
	 */
	public static String compress(Context context, String srcPath, double maxSize) {
		float hh = DisplayUtils.getScreenHeight(context);
		float ww = DisplayUtils.getScreenWidth(context);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, opts);
		opts.inJustDecodeBounds = false;
		int w = opts.outWidth;
		int h = opts.outHeight;
		int size = 0;
		if (w <= ww && h <= hh) {
			size = 1;
		} else {
			double scale = w >= h ? w / ww : h / hh;
			double log = Math.log(scale) / Math.log(2);
			double logCeil = Math.ceil(log);
			size = (int) Math.pow(2, logCeil);
		}
		opts.inSampleSize = size;
		bitmap = BitmapFactory.decodeFile(srcPath, opts);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int quality = 100;
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		System.out.println(baos.toByteArray().length);
		while (baos.toByteArray().length > maxSize * 1024 *1024) {
			baos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			quality -= 20;
			System.out.println(baos.toByteArray().length);
		}
		try {
			baos.writeTo(new FileOutputStream(srcPath));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				baos.flush();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return srcPath;
	}
}
