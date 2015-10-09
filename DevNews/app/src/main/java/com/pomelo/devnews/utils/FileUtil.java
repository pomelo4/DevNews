package com.pomelo.devnews.utils;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Looper;

import java.io.File;

public class FileUtil {

	/**
	 * 获取文件夹总大小
	 * @param file
	 * @return
	 */
	public static double getDirSize(File file) {
		//判断文件是否存在
		if (file.exists()) {
			//如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children) {
					size += getDirSize(f);
				}
				return size;
			} else {//如果是文件则直接返回其大小,以“兆”为单位
				return (double) file.length() / 1024 / 1024;
			}
		} else {
			return 0.0;
		}
	}


	/**
	 * 保存图片
	 *
	 * @param activity
	 * @param picUrl
	 */
	public static void savePicture(Activity activity, String picUrl) {

//		boolean isSmallPic = false;
//		String[] urls = picUrl.split("\\.");
//		File cacheFile = ImageLoader.getInstance().getDiskCache().get(picUrl);
//
//		ImageLoader.build(activity).loadBimap

		//如果是GIF格式，优先保存GIF动态图，如果不存在，则保存缩略图
//		if (!cacheFile.exists()) {
//			String cacheUrl = picUrl.replace("mw600", "small").replace("mw1200", "small")
//					.replace("large", "small");
//			cacheFile = ImageLoader.getInstance().getDiskCache().get(cacheUrl);
//			isSmallPic = true;
//		}
//
//		File picDir = new File(CacheUtil.getSaveDirPath());
//
//		if (!picDir.exists()) {
//			picDir.mkdir();
//		}
//
//		final File newFile = new File(picDir, CacheUtil.getSavePicName(cacheFile, urls));
//
//		if (FileUtil.copyTo(cacheFile, newFile)) {
//			//保存成功之后，更新媒体库
//			MediaScannerConnection.scanFile(activity, new String[]{newFile
//					.getAbsolutePath()}, null, new MyMediaScannerConnectionClient(isSmallPic,
//					newFile));
//		} else {
//			ShowToast.Short(ToastMsg.SAVE_FAILED);
//		}

	}

	/**
	 * 用于保存图片后刷新图片媒体库
	 */
	private static class MyMediaScannerConnectionClient implements MediaScannerConnection
			.MediaScannerConnectionClient {

		private boolean isSmallPic;
		private File newFile;

		public MyMediaScannerConnectionClient(boolean isSmallPic, File newFile) {
			this.isSmallPic = isSmallPic;
			this.newFile = newFile;
		}

		@Override
		public void onMediaScannerConnected() {

		}

		@Override
		public void onScanCompleted(String path, Uri uri) {
			Looper.prepare();
			if (isSmallPic) {
//				ShowToast.Short(ToastMsg.SAVE_SMALL_SUCCESS + " \n相册" + File.separator + CacheUtil
//						.FILE_SAVE + File.separator + newFile.getName());
			} else {
//				ShowToast.Short(ToastMsg.SAVE_SUCCESS + " \n相册" + File.separator + CacheUtil
//						.FILE_SAVE + File.separator + newFile.getName());
			}
			Looper.loop();
		}
	}

}
