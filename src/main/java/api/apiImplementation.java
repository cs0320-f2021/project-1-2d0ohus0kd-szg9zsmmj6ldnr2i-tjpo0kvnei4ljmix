package api;

import java.util.Collection;

public class apiImplementation<T> implements apiInterface<T> {

    @Override
    public Collection<T> getFromJson(String path) {
        return null;
    }

    @Override
    public Collection<T> getFromAPI() {
        return null;
    }

    @Override
    public Collection<T> getField() {
        return null;
    }
}
