package cl.desafiolatam.yerkos.flashg8.views.main.finder;

public interface FinderCallback {

    void error(String error);
    void success();
    void notFound();
}
