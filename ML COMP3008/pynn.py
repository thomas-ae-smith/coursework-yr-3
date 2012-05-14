
print "hello World."

# Open a file for reading
in_file = open('semeion.data', 'r')

# Get all the lines
lines = in_file.readlines()

# Loop through all the lines and count the words
num_words = 0
for line in lines:
    words = line.split()  # split the line (based on whitespaces)
    num_words = num_words + len(words)

# Ouput the number of lines
print 'Number of lines', len(lines)
print 'Number of words', num_words