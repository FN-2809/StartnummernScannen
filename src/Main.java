import com.formdev.flatlaf.FlatLightLaf;
import view.DetailView;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        DetailView detailView = new DetailView();
        //SwingUtilities.invokeLater(DetailView::new);
    }
}
