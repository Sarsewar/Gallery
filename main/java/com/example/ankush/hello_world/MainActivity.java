package com.example.ankush.hello_world;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;
import android.widget.SeekBar;


public class MainActivity extends Activity implements OnClickListener
{
    private ImageView m1, m2, m3, m4, m5;
    static int fb, c = 1;
    static int flag = 0, progress;
    private ImageView vi;
    private Drawable t;
    SeekBar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m1 = (ImageView) findViewById(R.id.img1);
        m2 = (ImageView) findViewById(R.id.img2);
        m3 = (ImageView) findViewById(R.id.img3);
        m4 = (ImageView) findViewById(R.id.img4);
        m5 = (ImageView) findViewById(R.id.img5);

        vi = (ImageView) findViewById(R.id.imageView1);
       
	   vi.setOnCreateContextMenuListener(this);
/*
*This setonCreateContextMenuListener(); is for calling context menu
*/
        m1.setOnClickListener(this);
        m2.setOnClickListener(this);
        m3.setOnClickListener(this);
        m4.setOnClickListener(this);
        m5.setOnClickListener(this);

        sb = (SeekBar) findViewById(R.id.sb_value);
		/*
			*SeekBar is invisible until we select some option
		*/
        sb.setVisibility(View.INVISIBLE);
        sb.setMax(100);
        sb.setProgress(5);
    }
/*
*Func() will perform the calling operation of Brightness and Contrast using seekBar
*/
    public void func()
    {
        sb.setVisibility(View.VISIBLE);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //set the progress value 
				if (flag == 1)
                    vi.setColorFilter(brightIt(progress));
				
                if (flag == 2)
                    vi.setColorFilter(contrastIt(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (flag == 1) {
                    progress = seekBar.getProgress();
                    fb = progress;
                    vi.setColorFilter(brightIt(seekBar.getProgress()));
            }
                /*if (flag == 2) {
                    progress = seekBar.getProgress();
                    c = progress;
                    vi.setColorFilter(contrastIt(seekBar.getProgress()));
                }*/
            }
        });

    }
/*
*Context Menu main option  set karne ke liye OnCreateContextMenu
*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info) {
        super.onCreateContextMenu(menu,v,info);
        menu.setHeaderTitle("Select The Action");
        MenuItem mnu = menu.add(0, v.getId(), 0, "Contrast");
        MenuItem mnu1 = menu.add(0, v.getId(), 0, "Rotate");
        MenuItem mn2 = menu.add(0, v.getId(), 0, "Brightness");

    }
/*
*Option select hone ke baad operation perform karne ke liye 
*/
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Brightness") {
            //brightness logic
            flag=1;
            func();


        }
        else if(item.getTitle()=="Rotate")
        {
            //imageView Should be rotate by 90 degree
            vi.setRotation(vi.getRotation() + 90);

        }
        else if(item.getTitle()=="Contrast")
        {
                //Contrast Logic
                flag=2;
                func();
        }
        else {


            return true;
        }
        return false;
    }

/*
*/
    @Override
    public void onClick(View v)
    {
        sb.setVisibility(View.GONE);//To set The SeekBar invisible
        vi.setRotation(0);//To repostioning the Image to default i.e, 0 degree
        sb.setProgress(5);//set to default progress 
        vi.setColorFilter(0);//set Contrast Setting to default, so that effect won't be affect to other image

        switch (v.getId()) {
            case R.id.img1:
                vi.setImageDrawable(m1.getDrawable());

                break;

            case R.id.img2:
                vi.setImageDrawable(m2.getDrawable());
                break;

            case R.id.img3:
                vi.setImageDrawable(m3.getDrawable());
                break;

            case R.id.img4:
                vi.setImageDrawable(m4.getDrawable());
                break;

            case R.id.img5:
                vi.setImageDrawable(m5.getDrawable());
                break;

        }


    
	
	}
	
	/**Brightness logic :To change the Brightnessusing ColorMatrixColorFilter
	*/
    public static ColorMatrixColorFilter brightIt(int fb) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[]{
                c, 0, 0, 0, fb,
                0, c, 0, 0, fb,
                0, 0, c, 0, fb,
                0, 0, 0, 1, 0});

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);

        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);

        return f;
    }

/*
*This ColorMatrixColorFilter will change the constrast value 
*which is called from SeekBar's OnProgressChange 
*/
    public static ColorMatrixColorFilter contrastIt(int c) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[]{
                c, 0, 0, 0, fb,
                0, c, 0, 0, fb,
                0, 0, c, 0, fb,
                0, 0, 0, 1, 0});

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);

        return f;
    }



    }
