dir = out

$(dir):
	mkdir -p $(dir)

all: $(dir)
	javac -d $(dir) *.java

run: all
	java -cp $(dir) jlox.Lox $(FILE)