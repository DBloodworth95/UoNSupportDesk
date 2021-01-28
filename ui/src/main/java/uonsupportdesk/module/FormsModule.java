package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import uonsupportdesk.controller.FormsController;
import uonsupportdesk.view.FormsView;

import java.util.Objects;

public class FormsModule extends WorkbenchModule {

    private FormsController formsController;

    public FormsModule() {
        super("Form Repository", MaterialDesignIcon.FILE_DOCUMENT);
    }

    @Override
    public Node activate() {
        if (Objects.isNull(formsController)) {
            FormsView formsView = new FormsView();
            formsController = new FormsController(formsView);
        }
        return formsController.initView();
    }
}
