package ht.skyd.it_ebooks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * An activity representing a list of Books. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BookListActivity extends AppCompatActivity {

    private interface BookListClient {
        @GET("search/{query}")
        Call<BookList> books(
                @Path("query") String query
                //@Path("page_num") String page_num
        );
    }

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private  RecyclerView mRecyclerView;
    private BookItemRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final ProgressDialog dialog = ProgressDialog.show(this, "", "loading...");

        mRecyclerView = (RecyclerView) findViewById(R.id.book_list);
        assert mRecyclerView != null;
        BookListClient mClient = ServiceGenerator.createService(BookListClient.class);
        Call<BookList> call = mClient.books("php mysql");
        call.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                dialog.dismiss();
                Log.d("MainActivity", "Status Code = " + response.code());
                if (response.isSuccess()) {
                    // TODO, error catching
                    // request successful (status code 200, 201)
                    BookList result = response.body();
                    Log.d("MainActivity", "response = " + new Gson().toJson(result));
                    List<Book> books = result.getBooks();
                    Log.d("MainActivity", "Books = " + books.size());
                    adapter = new BookItemRecyclerViewAdapter(books);
                    mRecyclerView.setAdapter(adapter);
                } else {
                    // response received but request not successful (like 400,401,403 etc)
                    //Handle errors

                }
            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                t.printStackTrace();
            }
        });

        if (findViewById(R.id.book_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


    }


    public class BookItemRecyclerViewAdapter
            extends RecyclerView.Adapter<BookItemRecyclerViewAdapter.ViewHolder> {

        private final List<Book> mBooks;

        public BookItemRecyclerViewAdapter(List<Book> books) {
            mBooks = books;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBook = mBooks.get(position);
            Picasso.with(holder.mCoverView.getContext())
                    .load(Uri.parse(holder.mBook.getImage()))
                            .into(holder.mCoverView);
            holder.mTitleView.setText(holder.mBook.getTitle());
            holder.mDescriptionView.setText(
                    (holder.mBook.getDescription() != null) ? holder.mBook.getDescription() : "");

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putLong("book_id", holder.mBook.getID());
                        BookDetailFragment fragment = new BookDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.book_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, BookDetailActivity.class);
                        intent.putExtra("book_id", holder.mBook.getID());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mCoverView;
            public final TextView mTitleView;
            public final TextView mDescriptionView;
            public Book mBook;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mCoverView = (ImageView) view.findViewById(R.id.list_cover_view);
                mTitleView = (TextView) view.findViewById(R.id.book_title);
                mDescriptionView = (TextView) view.findViewById(R.id.book_description);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTitleView.getText() + "'";
            }
        }
    }

//    public class BookListTask extends AsyncTask<String, Void, BookList> {
//        BookListClient mClient;
//
//        @Override
//        protected void onPreExecute(){
//            mClient = ServiceGenerator.createService(BookListClient.class);
//        }
//
//        @Override
//        protected BookList doInBackground(String... params) {
//            String query = params[0];
//            String page = params[1] != null ? params[1] : "";
//            Call<BookList> call = mClient.books();
//
//            try {
//                return call.execute().body();
//            } catch (IOException e) {
//                Log.e("Hash", "Response is null");
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(BookList result) {
//            if (result != null) {
//                mRecyclerView.setAdapter(new BookItemRecyclerViewAdapter(result.getBooks()));
//            }
//            else {
//
//            }
//        }
//
//    }
}
