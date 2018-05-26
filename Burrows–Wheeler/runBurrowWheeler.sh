echo "read [$1]"
echo "output to [$2]"
java-coursera BurrowsWheeler - < "$1" | java-coursera BurrowsWheeler + > "$2"
