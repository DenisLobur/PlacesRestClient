package denis.places.app.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import denis.places.app.*;
import denis.places.app.model.Predictions;
import denis.places.app.presenter.PlacesPresenter;
import denis.places.app.presenter.Presenter;

import java.util.List;

public class MainActivity extends AbstractPresenterActivity implements PlacesPresenter.PlacesPresenterView {

    @Bind(R.id.search_field)
    EditText searchField;

    @Bind(R.id.search_btn)
    Button searchBtn;

    @Bind(R.id.places_recycler_view)
    RecyclerView placesList;

    private PlacesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        placesList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        placesList.setLayoutManager(layoutManager);

        adapter = new PlacesAdapter();
        placesList.setAdapter(adapter);

    }

    @Override
    public Presenter onCreatePresenter() {
        return new PlacesPresenter(getApp());
    }

    @Override
    public PlacesPresenter getPresenter() {
        return (PlacesPresenter) super.getPresenter();
    }

    @Override
    public PresenterView getPresenterView() {
        return this;
    }

    @OnClick(R.id.search_btn)
    public void search() {
        getPresenter().searchPlaces(searchField.getText().toString());
    }

    @Override
    public void showData(List<Predictions> data) {
        adapter.swapData(data);
    }
}
