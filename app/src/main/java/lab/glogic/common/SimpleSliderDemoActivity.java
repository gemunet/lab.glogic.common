package lab.glogic.common;

import lab.glogic.common.simpleslider.SimpleSliderActivity;

public class SimpleSliderDemoActivity extends SimpleSliderActivity {

    @Override
    public int[] getStepLayouts() {
        return new int[]{
                R.layout.activity_simple_slider_demo_step1,
                R.layout.activity_simple_slider_demo_step2,
                R.layout.activity_simple_slider_demo_step3,
        };
    }
}
