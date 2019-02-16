package per.goweii.anydialog;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2018/10/24
 */
public interface OnDialogLifeListener {
    void onCreated(AnyDialog anyDialog);
    void onEnter(AnyDialog anyDialog);
    void onShown(AnyDialog anyDialog);
    void onExit(AnyDialog anyDialog);
    void onDismissed(AnyDialog anyDialog);
}
