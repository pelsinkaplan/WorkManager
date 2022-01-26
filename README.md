# Book Reminder

Bu repo WorkManager kütphanesi kullanılarak kullanıcıya istediği saat aralığında bildirim gönderen bir Kitap Okuma Hatırlatıcı diyerek adlandırabileceğimiz bir Android Projesinin kaynak kodlarını içermektedir.

**WorkManager nedir, ne için kullanılır?**

- Uygulama içerisinde zamanlanması, belirli şartların sağlaması sonucu çalışması veya belirli zaman aralıklarında çalıştırılması gereken işlemler için kullanılan bir JetPack kütüphanesidir.
- Ayrıca uygulama veya cihaz yeniden başlatıldığında, zamanlanmış görevlerin devam ettirilmesini sağlayan bir özellik de barındırır.
- WorkManager kullanabilmek için uygulama API 14 ve üstünü destekliyor olmalıdır.
- Mevcutta ağ var mı(WIFI), görev başlatılmadan delay verilmeli mi, giriş çıkışlar handle edilmeli mi gibi sorularda WorkManager özelleştirilebilir.
- Aynı zamanda pil dostu bir yapıdır.
- Tek seferlik çalışmalar için OneTimeWorkRequest, periyodik çalışmalar için ise PeriodicWorkRequest yapısı kullanılır.

**Worker :** Arka planda gerçekleşecek görevin tanımlandığı class. Worker classını extend eder ve doWork() metodunu override ederek çalışır.
**WorkRequest :** Worker üzerinde belirtilen görevin ne zaman ve nasıl çalışacağını yapılandırmak için kullanılan yapıdır. Şartları özelleştirmek için Constraints yapısı kullanılabilir.
**WorkManager :** WorkRequest'i planlayan ve çalıştıran class'tır.

Bu projede kullanıcının tercihine göre belirlenen saat aralıklarıyla periyodik olarak bildirim gönderme işlemi yapılmıştır. Kitap okumayı belirli saat aralıklarında hatırlatmak amaçlanmıştır ve bildirim göndermek için Notification yapısı kullanılmıştır.

Uygulamanın çalışma gifi:

<img src="https://github.com/pelsinkaplan/WorkManager/blob/master/book_reminder_gif.gif" width="200" height="400">


Yukarıdaki gibi kullanıcı saati azaltıp arttırarak kendi isteiğine göre hatırlatma aralığı seçer. Ardından kullanıcı TAMAM butonuna bastığında neler oalcak?
- Öncelikle requestimiz tanımlanacaktır.
```
PeriodicWorkRequestBuilder<BookWorker>(hour.toLong(), TimeUnit.HOURS).build()
```
- Periyodik bir çalışma sunacağımız için PeriodicWorkRequestBuilder kullandık.
- Saatlik bir değişim planladığımız için bunu requestimizde belirttik. 
- Oluşturduğumuz Worker(BookWorker) classını da dahil ederek requestimizi oluşturduk.
- Periyodik düzende kullanıcının tercih etmiş olduğu saat aralığı bittikten sonra BookWorker classımızdaki doWork() metodu çalışacak ve BookWorker classı içerisinde tanımlamış olduğumuz Notification çalışacaktır.



