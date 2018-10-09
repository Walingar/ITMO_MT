my $str = "";
while ($current = <>){
    $str = $str.$current;
}

my @links = ();
push(@links, $+{link}) while $str =~ m{<a(.*?)href=["'](?<link>.*?)["'](.*?)>}gx;

my %set_links;
for $link (@links) {
    #print "$link\n";
    $link =~ m{(//(.*?(:.*?)?@)?(?<host1>.*?)([/#?;:]|$))|((.*?(:.*?)?@)?(?<host2>[^/]*?):(\d+)([/#?;:]|$))}gx;
    $set_links{$+{host1}} = 1;
    $set_links{$+{host2}} = 1;
}

for $link (sort keys %set_links) {
    print "$link\n" if length $link != 0;
}