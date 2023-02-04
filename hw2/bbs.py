"""
file: bbs.py

CSCI 462 HW 2
Blum Blum Shub number generator

@author Derek Garcia
"""
import sys


def bbs(length, x, n):
    """
    Performs the BBS algorithm

    :param length: length of keysteam to generate
    :param x: seed
    :param n: factoring value
    :return: keystream of bits
    """
    keystream = ""
    for i in range(length):
        x = x ** 2 % n
        keystream += str(x % 2)

    return keystream


def decrypt(keystream, ciphertext):
    if len(keystream) != len(ciphertext):
        print("keystream and ciphertext length missmatch")
        exit()

    plaintext_bits = ""
    for i in range(len(ciphertext)):
        bit = int(keystream[i]) ^ int(ciphertext[i])
        plaintext_bits += str(bit)
    return plaintext_bits


def to_seven_ascii(bits):
    if (len(bits) % 7) != 0:
        print("bit mismatch")
        return

    ascii_chars = []
    for i in range(0, len(bits), 7):
        ascii_chars.append(int(bits[i:i+7], 2))

    return ascii_chars


if __name__ == '__main__':
    """
   Computes BBS and deciphers
   
   :param bbs_length: number of bits to generate
   :param seed: seed value to use
   :param factoring value: factoring value to use
   :param ciphertext: string of ciphertext bits (optional)
   """

    # Check if args are good
    if len(sys.argv) != 4 and len(sys.argv) != 5:
        print("usage: bbs.py <number of bits to generate> <seed> <factoring value>")
        print("usage: bbs.py <number of bits to generate> <seed> <factoring value> <ciphertext bits>")
        exit()

    keystream = bbs(int(sys.argv[1]), int(sys.argv[2]), int(sys.argv[3]))
    print(f"keystream: {keystream}")

    if len(sys.argv) == 5:
        plaintext_bits = decrypt(keystream, sys.argv[4])
        print(f"plaintext: {plaintext_bits}")
        plaintext = to_seven_ascii(plaintext_bits)
        print(f"7 bit ascii: {plaintext}")
