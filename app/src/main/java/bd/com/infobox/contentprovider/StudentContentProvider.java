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
        String id = uri.getLastPathSegment();
        return database.delete(MyDatabaseHelper.TABLE_STD_INFO,id,null);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long inserted_row = database.insert(MyDatabaseHelper.TABLE_STD_INFO, null, values);
        Uri inserted_uri = Uri.parse(CONTENT_STRING+"/"+MyDatabaseHelper.TABLE_STD_INFO+"/"+inserted_row);

        return inserted_uri;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new MyDatabaseHelper(getContext());
        database = databaseHelper.getWritableDatabase();

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (URI_MATCHER.match(uri)){
            case 1:
                return database.rawQuery("SELECT * FROM " + MyDatabaseHelper.TABLE_STD_INFO, null);
            case 2:
                String id = uri.getLastPathSegment();
                return database.rawQuery("SELECT * FROM "
                        +MyDatabaseHelper.TABLE_STD_INFO+" WHERE "+MyDatabaseHelper.COL_ID+" = "+id,
                        null);
                default:
                    throw new UnsupportedOperationException("Invalid Uri");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");

    }
}
