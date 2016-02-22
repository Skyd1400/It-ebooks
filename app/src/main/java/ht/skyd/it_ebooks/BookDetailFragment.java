package ht.skyd.it_ebooks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ht.skyd.it_ebooks.dummy.DummyContent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * A fragment representing a single Book detail screen.
 * This fragment is either contained in a {@link BookListActivity}
 * in two-pane mode (on tablets) or a {@link BookDetailActivity}
 * on handsets.
 */
public class BookDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final Long ARG_BOOK_ID = Long.valueOf(0);

    /**
     * The mBook variable this fragment is presenting.
     */
    private Book mBook;

    private Call<Book> mCall;


    private ProgressDialog mDialog;

    /**
     * Interface describing the client to get the book data
     */
    private interface BookDetailClient {
        @GET("book/{id}")
        Call<Book> book(
                // will be the book isbn
                @Path("id") Long book_id
        );
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("book_id")) {
            mDialog.show(getContext(), "", "loading...");
            Activity activity = this.getActivity();
            // Create the service
            BookDetailClient mClient = ServiceGenerator.createService(BookDetailClient.class);
            // get the call object to retrieve the book data
            mCall = mClient.book(getArguments().getLong("book_id"));
            // add the callback;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.book_detail, container, false);

        final Activity activity = this.getActivity();
        mCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                mDialog.dismiss();
                Log.d("MainActivity", "Status Code = " + response.code());
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    mBook = response.body();

                    CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                    if (appBarLayout != null) {
                        appBarLayout.setTitle(mBook.getTitle());
                    }

                }
                else {

                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }
        });

        return rootView;
    }
}
