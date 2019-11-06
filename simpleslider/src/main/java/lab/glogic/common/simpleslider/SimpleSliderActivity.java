package lab.glogic.common.simpleslider;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public abstract class SimpleSliderActivity extends AppCompatActivity {

    private int[] mStepLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_slider);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        // layouts of all sliders
        // add few more layouts if you want
        mStepLayouts = getStepLayouts();

        // adding bottom dots
        addBottomDots(0);

        final Button btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSkip.setEnabled(false);
                btnSkip.setAlpha(.5f);
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });

        final Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewPager.getCurrentItem() + 1;
                if(current < mStepLayouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    btnNext.setEnabled(false);
                    btnNext.setAlpha(.5f);
                    setResult(RESULT_OK, null);
                    finish();
                }
            }
        });

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);

                // changing the next button text 'NEXT' / 'GOT IT'
                if (position == mStepLayouts.length - 1) {
                    // last page. make button text to GOT IT
                    btnNext.setText(getString(R.string.simpleslider_complete));
                    btnSkip.setVisibility(View.GONE);
                } else {
                    // still pages are left
                    btnNext.setText(getString(R.string.simpleslider_next));
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

    }


    public abstract int[] getStepLayouts();

    private void addBottomDots(int currentPage) {
        LinearLayout dotsLayout = findViewById(R.id.layoutDots);
        TextView[] dots = new TextView[mStepLayouts.length];

        int colorActive = getResources().getColor(R.color.simpleslider_dot_active);
        int colorInactive = getResources().getColor(R.color.simpleslider_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorActive);
        }
    }


    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(mStepLayouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mStepLayouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
