all: clean tests compile
tests:
	lein test :all
clean: 
	lein clean
	lein cljs-clean!
compile:
	lein cljs-build!
	lein with-profile prod uberjar
run: 
	java -jar target/webapp.jar
