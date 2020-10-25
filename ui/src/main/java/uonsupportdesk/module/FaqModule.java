package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import uonsupportdesk.view.FaqView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class FaqModule extends WorkbenchModule {

    private FaqView faqView;

    public FaqModule() {
        super("FAQ", MaterialDesignIcon.COMMENT_QUESTION_OUTLINE);
    }

    @Override
    public Node activate() {
        if (Objects.isNull(faqView)) {
            faqView = new FaqView();
        }
        return faqView;
    }
}
