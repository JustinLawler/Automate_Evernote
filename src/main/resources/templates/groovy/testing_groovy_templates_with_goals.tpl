h1 ('Simple groovy template for unit testing')
h2 ("sub-header - ${DATE.full_date}")
newLine()
p ("one")
p ("w${DATE.week_num} - ${DATE.day}")
newLine()
h2 ("Top Goals - Week ${DATE.week_num}")
ul {
   TEXT_FILE.test_goals1.each { 
      li(it)
   }
}
newLine()
h2 ("Medium Goals - Week ${DATE.week_num}")
ul {
   TEXT_FILE.test_goals2.each { 
      li(it)
   }
}
newLine()

p ("The End")