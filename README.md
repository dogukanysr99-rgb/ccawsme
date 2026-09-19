# ccawsme — Kişisel Android Uygulamaları

Bu repo, bir avukatın günlük işini kolaylaştıracak küçük Android
uygulamalarını barındıran çok modüllü (multi-module) bir Gradle projesidir.
Her modül bağımsız bir uygulama olarak derlenip telefona kurulabilir.

## Modüller

### `app` — Emsal Defterim

Beğenilen emsal kararları ve kişisel notları kaydedip arayabileceğiniz,
tamamen cihaz üzerinde çalışan (offline-first) bir uygulama.

- Emsal karar ekleme/düzenleme: başlık, mahkeme/daire, karar no, karar
  tarihi, özet, emsal metni, kişisel notlar ve etiketler
- Başlık, mahkeme, özet, metin, not ve etiketlerde anlık arama
- Favori işaretleme, detayda metni panoya kopyalama
- Veriler yalnızca cihazda (Room/SQLite) saklanır, internet gerekmez

### `davaustasi` — Dava Ustası

Hukuk büronuzu bir "imparatorluğa" dönüştürdüğünüz idle/tycoon
simülasyonu (Mafia Life tarzı mekaniklerden esinlenilmiştir).

- **Büro kurma**: Adınızı girip çevrimiçi (DiceBear) bir avatar
  seçersiniz; avatar dilediğiniz an yeniden oluşturulabilir
- **Idle gelir**: Saniyede otomatik para kazanırsınız; gelir, işe
  aldığınız kadronuzun ve fethettiğiniz bölgelerin toplamından oluşur
- **Büro kadro ağacı**: Siz liderin altında 3 ortak avukat, onların
  altında da (ortak işe alındıktan sonra açılan) birer stajyer —
  her biri avatarı, yaşı ve saniyelik geliriyle görünen bağlı bir
  ağaç halinde
- **Şehir haritası**: 6 bölgeyi (Kadıköy, Beşiktaş, Şişli, Üsküdar,
  Bakırköy, Maltepe) müvekkil potansiyeli ve itibar eşiğine göre
  parayla fethederek müvekkil sayınızı ve gelirinizi artırırsınız
- **Olay kartları**: "Sonraki Gün"e bastığınızda portreli bir olay
  kartı çıkar (müvekkil talebi, zam isteyen çalışan, gazeteci
  röportajı vb.), her seçenek anlık para/itibar etkisi gösterir
- **Üst HUD**: Para (+gelir/sn), itibar yüzdesi, toplam müvekkil
  sayısı sürekli görünür; günde bir kez alınabilen günlük ödül var
- İlerleme cihazda (DataStore) saklanır; avatarlar çevrimiçi
  yüklendiği için uygulama internet erişimi ister

## Teknoloji

- Kotlin, Jetpack Compose (Material 3)
- `app`: Room (yerel veritabanı), Navigation Compose
- `davaustasi`: DataStore Preferences (oyun durumu kalıcılığı)
- Her iki modülde de ViewModel + StateFlow (MVVM)

## Geliştirme ortamı

- Android Studio (Koala veya üzeri önerilir)
- JDK 17
- minSdk 26, targetSdk/compileSdk 34

Projeyi Android Studio ile açıp senkronize ettikten sonra istediğiniz
modülü (`app` ya da `davaustasi`) doğrudan çalıştırabilirsiniz. Bu proje
bir sandbox ortamında (Google'ın Maven deposuna ağ erişimi olmadan)
hazırlandığı için burada `gradlew build` ile derleme doğrulanamadı;
GitHub Actions üzerindeki "APK Derle" / "Dava Ustası APK Derle"
workflow'ları her push'ta ilgili modülün debug APK'sını derleyip
Artifacts olarak yayınlar — telefonunuza kurmak için oradan
indirebilirsiniz.
