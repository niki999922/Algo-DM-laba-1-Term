while (<>) {
  s/(\(.*?\))/()/g;
  print;
}
