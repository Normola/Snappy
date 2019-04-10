package uk.co.undergroundbunker.snappy;

import android.content.Context;
import android.os.AsyncTask;

import java.io.InputStream;
import java.util.List;

public class SaveDataTask extends AsyncTask<DataModel, Void, Void> {
    private final Context _context;

    public SaveDataTask(Context context)
    {
        _context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(DataModel... params)
    {
        DataModel dataModel = params[0];

        try
        {
            Database database = new Database(_context);

            database.addData(dataModel);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }
}
