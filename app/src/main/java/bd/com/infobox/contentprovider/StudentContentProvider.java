package bd.com.infobox.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import bd.com.infobox.sqlitelibrary.DatabaseModule.MyDatabaseHelper;

public class StudentContentProvider extends ContentProvider {

    private MyDatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public static final String AUTHORITY = "bd.com.infobox.contentprovider";
    public static final String CONTENT_STRING = "content://"+AUTHORITY;
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    private static final UriMatcher URI_MATCHER;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, MyDatabaseHelper.TABLE_STD_INFO, 1); // only for insert & query
        URI_MATCHER.addURI(AUTHORITY, MyDatabaseHelper.TABLE_STD_INFO+"/#", 2); // for delete & update
    }

    public StudentContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long inserted_row = database.insert(MyDatabaseHelper.TABLE_STD_INFO, null, values);
        Uri inserted_uri = Uri.parse(CONTENT_STRING+"/"+MyDatabaseHelper.TABLE_STD_INFO+"/"+inserted_row);

        return inserted_uri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        databaseHelper = new MyDatabaseHelper(getContext());
        database = databaseHelper.getWritableDatabase();

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
