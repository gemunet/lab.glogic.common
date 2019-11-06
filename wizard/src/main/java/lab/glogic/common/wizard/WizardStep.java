package lab.glogic.common.wizard;

public class WizardStep {
    private Class<? extends WizardFragment> stepClass;
    private Class<? extends WizardFragment> auxClass;
    private Integer resIcon;

    public WizardStep(Class<? extends WizardFragment> stepClass, Integer resIcon) {
        this.stepClass = stepClass;
        this.resIcon = resIcon;
    }

    public WizardStep(Class<? extends WizardFragment> stepClass, Class<? extends WizardFragment> auxClass, Integer resIcon) {
        this.stepClass = stepClass;
        this.auxClass = auxClass;
        this.resIcon = resIcon;
    }

    public Class<? extends WizardFragment> getStepClass() {
        return stepClass;
    }

    public void setStepClass(Class<? extends WizardFragment> stepClass) {
        this.stepClass = stepClass;
    }

    public Class<? extends WizardFragment> getAuxClass() {
        return auxClass;
    }

    public void setAuxClass(Class<? extends WizardFragment> auxClass) {
        this.auxClass = auxClass;
    }

    public Integer getResIcon() {
        return resIcon;
    }

    public void setResIcon(Integer resIcon) {
        this.resIcon = resIcon;
    }
}
