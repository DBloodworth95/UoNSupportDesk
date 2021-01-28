package uonsupportdesk.controller;

import uonsupportdesk.view.FormsView;

public class FormsController {

    private final FormsView formsView;

    public FormsController(FormsView formsView) {
        this.formsView = formsView;
    }

    public FormsView initView() {
        attachButtonListeners();

        return formsView;
    }

    private void attachButtonListeners() {

    }
}
