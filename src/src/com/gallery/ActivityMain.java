package src.com.gallery;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gallery.R;

public class ActivityMain extends Activity {
	
	private List<View> listViews;
	 String[] images = {
 			"http://pic7.nipic.com/20100517/758481_103638095195_2.jpg",
 			"http://pic4.nipic.com/20091107/2823451_101514004995_2.jpg",
 			"http://pica.nipic.com/2008-03-17/2008317174743584_2.jpg",
 			"http://pic8.nipic.com/20100719/1295091_101554405346_2.jpg",
 			"http://pic21.nipic.com/20120520/9502787_233945518000_2.jpg",
 			"http://pic21.nipic.com/20120520/9502787_234324132000_2.jpg",
 			"http://pic21.nipic.com/20120520/9502787_235125452000_2.jpg",
 			"http://pic21.nipic.com/20120520/9502787_234619132000_2.jpg",
 			"http://pic21.nipic.com/20120521/9502787_000719973000_2.jpg"};
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.layout_gallery);
        
         
    	listViews = new ArrayList<View>();
        ImageAdapter adapter = new ImageAdapter(this, images,listViews);
//        adapter.createReflectedImages();

        GalleryFlow galleryFlow = (GalleryFlow) findViewById(R.id.Gallery01);
        galleryFlow.setAdapter(adapter);
        
}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				if (listViews == null || 0 == listViews.size())
					return;

				// 图片读取完成后进行加载，隐藏ProgressBar

				int p = msg.arg1;
				Bitmap b = msg.obj == null ? null : (Bitmap) msg.obj;
				View v = (listViews.get(p));

				((ImageView) v.findViewById(R.id.pic)).setImageBitmap(b);
				// ((ProgressBar) v.findViewById(R.id.progress))
				// .setVisibility(View.GONE);
				((ProgressBar) v.findViewById(R.id.progress))
						.setVisibility(View.INVISIBLE);

				break;

			default:
				break;
			}
		}
	};
	
	protected void onResume() {
		super.onResume();
		pageswitch();
		
	};
	
	private void pageswitch() {
		listViews.clear();

		int max = images.length;
		for (int i = 0; i < max; i++) {
			listViews.add(addView());
		}


		// 初始读取第一张图
		HttpClient.getOriginalPic(images[0], 0, mHandler);
		

	}

	private View addView() {
		return View.inflate(this, R.layout.item_pic, null);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			listViews.clear();
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}

}