package com.test.moviegallery.di;

import com.test.moviegallery.data.constant.ConstData;
import com.test.moviegallery.service.MovieApiInterface;
import com.test.moviegallery.repository.MovieRepository;
import com.test.moviegallery.repository.MovieRepositoryImpl;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public MovieApiInterface provideMovieApi(){

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type", "application/json");
                requestBuilder.header("Authorization", "Bearer "+ ConstData.accessToken);
                return chain.proceed(requestBuilder.build());
            }
        });
        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(MovieApiInterface.class);
    }

    @Provides
    @Singleton
    public MovieRepository provideMovieRepository(MovieApiInterface movieApiInterface){
        return new MovieRepositoryImpl(movieApiInterface);
    }

}
