Program pobiera dane z randomuser korzystając z Retrofita działającego pod RxJavą. Ilość danych (jak i całe zapytanie do serwera) jest zapisane na sztywno w interface do retrofita (folder ApiService). Dla uproszczenia model został znacznie okrojony w stosunku do tego co można pobrać z api. 

Dane wyświetlają się w recyclerView. Kopia danych jest w liście pomocniczej, która jest pobierana do adaptera, jeśli zostaną wyczyszczone wyniki wyszukiwania. Wyszukiwanie jest jednoczesne dla imienia i nazwiska. 

Zostały zaimplementowane funkcje wyszukiwania, oznaczania ulubionych i wyświetlenia detali. Aplikacja nie przechowuje pobranych danych w sposób trwały. 

