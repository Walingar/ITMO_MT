while (<>) {
    s/\(([^\)]*)\)/()/g;
    print;
}