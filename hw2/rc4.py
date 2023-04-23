"""


@author Derek Garcia
"""

plaintext = "01000010 01000001 01010010 01000001 01000011 01001011 01001111 01000010 01000001 01001101 01000001".replace(" ", "")
ciphertext = "01000011 00011011 00010010 00110000 11111000 10100111 10001110 11101001 00010100 00011101 01100100".replace(" ", "")

ks_temp = []
byte = ""
for i in range(len(ciphertext)):
    bit = int(plaintext[i]) ^ int(ciphertext[i])


    # if len(byte) < 8:
    #     byte += str(bit)
    # else:
    #     ks_temp.append(byte)
    #     byte = ""


print(ks_temp)
ks_temp = "00000001 01011010 01000000 01110001 10111011 11101100 11000001 10101011 01010101 01010000 00100101".split(" ")
ks = ""
for i in ks_temp:
    num = int(i, 2) + 1
    ks += '{0:08b}'.format(num)

print(ks)

pt2 = ""
byte = ""
ciphertext2="01000110 00010100 00001111 00110011 11110000 10101001 10010110 11111110 00000011 00011100 01110110".replace(" ", "")
for i in range(len(ciphertext2)):
    bit = int(ks[i]) ^ int(ciphertext2[i])
    pt2 += str(bit)

print(pt2)