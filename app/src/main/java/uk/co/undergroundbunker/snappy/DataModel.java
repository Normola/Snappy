package uk.co.undergroundbunker.snappy;


public class DataModel {
    private String _name;
    private byte[] _bitmap;

    public String getName()
    {
        return _name;
    }

    public byte[] getBitmap()
    {
        return _bitmap;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setBitmap(byte[] bitmap)
    {
        _bitmap = bitmap;
    }
}
