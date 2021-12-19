from __future__ import division
from assets import diagnostic_report


def calculate_gamma_rate(diagnostic_report):
    most_common_bits = ''
    bit_counts = [0 for i in range(len(diagnostic_report[0]))]
    for binary_number in diagnostic_report:
        for i in range(len(binary_number)):
            if binary_number[i] == '1': bit_counts[i] += 1
    for number in bit_counts:
        if number > len(diagnostic_report) / 2: most_common_bits += '1'
        else: most_common_bits += '0'
    return most_common_bits

def calculate_epsilon_rate(gamma_rate):
    epsilon_rate = ''
    for number in gamma_rate:
        if number == '1': epsilon_rate += '0'
        else: epsilon_rate += '1'
    return epsilon_rate

def convert_binary_to_decimal(binary_number):
    decimal_number = 0
    for i in range(len(binary_number)):
        decimal_number += int(binary_number[len(binary_number)-i-1]) * 2**i
    return decimal_number


def main():
    gamma_rate_binary = calculate_gamma_rate(diagnostic_report)
    epsilon_rate_binary = calculate_epsilon_rate(gamma_rate_binary)
    gamma_rate_decimal = convert_binary_to_decimal(gamma_rate_binary)
    epsilon_rate_decimal = convert_binary_to_decimal(epsilon_rate_binary)
    print(gamma_rate_decimal * epsilon_rate_decimal)


if __name__ == "__main__":
    main()
