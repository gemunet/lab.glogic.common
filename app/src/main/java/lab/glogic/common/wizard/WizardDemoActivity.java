package lab.glogic.common.wizard;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import lab.glogic.common.R;

public class WizardDemoActivity extends WizardActivity {

    public final static String WIZARD_RESULT = "WIZARD_RESULT";

    private WizardViewModel wizardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wizardViewModel = ViewModelProviders.of(this).get(WizardViewModel.class);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_wizard_demo;
    }

    @Override
    protected List<WizardStep> getWizardSteps() {
        List<WizardStep> steps = new ArrayList<>();

        steps.add(new WizardStep(WizardStep1Fragment.class, R.drawable.ic_android_blue_24dp));
        steps.add(new WizardStep(WizardStep2Fragment.class, null));
        steps.add(new WizardStep(WizardStep3Fragment.class, R.drawable.ic_android_blue_24dp));

        return steps;
    }

    @Override
    public void finishWizard() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(WIZARD_RESULT, wizardViewModel.getExtras());
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
