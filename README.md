Technical Test

**Usage of Dependencies**

OkHttpClient with Retrofit
Retrofit is employed for invoking API calls, while OkHttpClient intercepts the requests and adds the movieDB's access token to the header of each request.

Dagger Hilt
Dagger Hilt is adopted for DI, offering easily understandable annotations and a straightforward setup. In the current project, DI primarily provide the repository and interface in ViewModel with the need of construct it.

**Architecture**

MVVM with LiveData and ViewBinding
Fragments/Activities observe LiveData instances created within the ViewModel. When an activity/fragment is created, it calls the ViewModel to load data from the API/local database. Upon disposal of an activity/fragment, the observed LiveData is also disposed to prevent memory leaks.
