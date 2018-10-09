my $str = "";
while (my $current = <>) {
    $str = $str.$current
}
$str =~ s/<.*?>//mg;
$str =~ s/^([ ]*\n)*//;
$str =~ s/([ ]*\n|[ ]*)*$//;
$str =~ s/([\s]*\n){2,}/\n\n/g;
$str =~ s/^[ ]*//mg;
$str =~ s/[ ]*$//mg;
$str =~ s/([ ])\1+/\1/g;
print $str;