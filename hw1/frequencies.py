"""
file: frequencies.py

CSCI 462 HW 1
Calculates character frequency from text file

@author Derek Garcia
"""
import sys

if __name__ == '__main__':
    """
    Takes a text file can calculates character frequency
    
    @param file path: path to target text file
    """

    # Check if args are good
    if len(sys.argv) != 2:
        print("usage: frequencies.py <file path>")
        exit()

    # Attempt to open file
    try:
        freq = {}  # track freq

        with open(sys.argv[1], encoding='utf-8', mode="r") as ciphertext:
            char_count = 0
            # Parse line by line
            for line in ciphertext:
                # Parse char by char
                for char in line.lower():
                    # Update freq if char is a letter
                    if char.isalpha():
                        # add to freq if not there
                        if char not in freq:
                            freq[char] = 0
                        freq[char] += 1
                        char_count += 1
    except Exception as e:
        print(f"Exception: {e}")
        exit()

    # sort dict from the highest freq to the lowest freq
    descending_freq = dict(sorted(freq.items(), key=lambda i: i[1], reverse=True))

    # print results
    print("Character Frequencies")
    for char in descending_freq:
        print(f"Char: {char} "
              f"| Occurrences: {descending_freq[char]} "
              f"| Freq: {round(freq[char] / char_count, 4)}"  # calc freq
              )
