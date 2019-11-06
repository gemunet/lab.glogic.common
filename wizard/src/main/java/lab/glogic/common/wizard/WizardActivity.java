package lab.glogic.common.wizard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public abstract class WizardActivity extends AppCompatActivity
        implements WizardFragment.WizardListener {

    private final static String TAG = "WizardActivity";
    private final static String AUX_STEP = "aux";

    private List<WizardStep> wizardSteps;
    private int wizardStepsCurrentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());

        wizardSteps = getWizardSteps();

        goSeek(wizardStepsCurrentItem);
    }

    protected int getLayoutView() {
        return R.layout.activity_wizard;
    }

    /**
     * Crea la lista de pasos que usara el wizard
     * @return
     */
    protected abstract List<WizardStep> getWizardSteps();

    public void goSeek(int pos) {
        Log.d(TAG, "goSeek step at " + pos);
        if(pos < 0 || pos >= wizardSteps.size()) return;

        try {
            WizardStep step = wizardSteps.get(pos);
            WizardFragment fragment = step.getStepClass().newInstance();
            changeFragment(fragment, wizardStepsCurrentItem >= pos, null);
            drawSteps(pos, false);

            wizardStepsCurrentItem = pos;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goNext() {
        if(getSupportFragmentManager().findFragmentByTag(AUX_STEP) != null) {
            goSeek(wizardStepsCurrentItem);
        } else {
            goSeek(wizardStepsCurrentItem + 1);
        }
    }

    @Override
    public void goBack() {
        if(getSupportFragmentManager().findFragmentByTag(AUX_STEP) != null) {
            goSeek(wizardStepsCurrentItem);
        } else {
            goSeek(wizardStepsCurrentItem - 1);
        }
    }

    @Override
    public void goAux() {
        try {
            WizardStep step = wizardSteps.get(wizardStepsCurrentItem);
            WizardFragment fragment = step.getAuxClass().newInstance();
            changeFragment(fragment, false, AUX_STEP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void skipWizard() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    @Override
    public void finishWizard() {
        setResult(RESULT_OK, null);
        finish();
    }

    @Override
    public void setChecked() {
        drawSteps(wizardStepsCurrentItem, true);
    }


    protected void changeFragment(WizardFragment fragment, boolean toBack, String tag) {
        if(toBack) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.fragment_container, fragment, tag)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(R.id.fragment_container, fragment, tag)
                    .commit();
        }

    }

    protected void changeStepFragment(int position, boolean toBack) {
        Log.d(TAG, "creating step at " + position);

        try {

            WizardStep res = wizardSteps.get(position);
            WizardFragment fragment = res.getStepClass().newInstance();

            if(toBack) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }


            drawSteps(position, false);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void drawSteps(int position, boolean forceCheck) {
        LinearLayout stepBar = findViewById(R.id.stepBar);
        LinearLayout successSteps = findViewById(R.id.successSteps);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,  1);

        stepBar.removeAllViews();
        successSteps.removeAllViews();
        ImageView lastSuccessIcon = null;
        for(int i=0; i<wizardSteps.size(); i++) {
            WizardStep res = wizardSteps.get(i);
            Integer icon = res.getResIcon();

            if(icon != null) {
                ImageView step = new ImageView(this);
                step.setImageResource(icon);
                if (i > position) {
                    step.setAlpha(0.2f);
                }
                stepBar.addView(step, params);

                ImageView success = new ImageView(this);
                success.setImageResource(R.drawable.wizard_step_check);
                success.setVisibility(View.INVISIBLE);

                if(i <= position) {
                    success.setVisibility(View.VISIBLE);
                    lastSuccessIcon = success;
                }

                successSteps.addView(success, params);
            }
        }
        if(lastSuccessIcon != null && !forceCheck) {
            lastSuccessIcon.setVisibility(View.INVISIBLE);
        }
    }

}
