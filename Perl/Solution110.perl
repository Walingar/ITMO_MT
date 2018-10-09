while (<>) {
    print if /\b([^\s]+)\1\b/;
}