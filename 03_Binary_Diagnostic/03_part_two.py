from __future__ import division
from assets import diagnostic_report


def calculate_oxygen_generator_rating(diagnostic_report):
    candidate_numbers = diagnostic_report
    for i in range(len(diagnostic_report[0])):
        bit_count = 0
        for binary_number in candidate_numbers:
            if binary_number[i] == '1': bit_count += 1
        if bit_count >= len(candidate_numbers) / 2: 
            candidate_numbers = [number for number in candidate_numbers if number[i] == '1']
        else:
            candidate_numbers = [number for number in candidate_numbers if number[i] == '0']
        if len(candidate_numbers) == 1:
            return candidate_numbers[0]
    return 'Error - multiple numbers cannot satisfy the condition for oxygen generator rating.'

def calculate_co2_scrubber_rating(diagnostic_report):
    candidate_numbers = diagnostic_report
    for i in range(len(diagnostic_report[0])):
        bit_count = 0
        for binary_number in candidate_numbers:
            if binary_number[i] == '1': bit_count += 1
        if bit_count < len(candidate_numbers) / 2: 
            candidate_numbers = [number for number in candidate_numbers if number[i] == '1']
        else:
            candidate_numbers = [number for number in candidate_numbers if number[i] == '0']
        if len(candidate_numbers) == 1:
            return candidate_numbers[0]
    return 'Error - multiple numbers cannot satisfy the condition for co2 scrubber rating.'

def convert_binary_to_decimal(binary_number):
    decimal_number = 0
    for i in range(len(binary_number)):
        decimal_number += int(binary_number[len(binary_number)-i-1]) * 2**i
    return decimal_number

def main():
    oxygen_generator_rating_binary = calculate_oxygen_generator_rating(diagnostic_report)
    co2_scrubber_rating_binary = calculate_co2_scrubber_rating(diagnostic_report)
    oxygen_generator_rating_decimal = convert_binary_to_decimal(oxygen_generator_rating_binary)
    co2_scrubber_rating_decimal = convert_binary_to_decimal(co2_scrubber_rating_binary)
    life_support_rating = oxygen_generator_rating_decimal * co2_scrubber_rating_decimal
    print(life_support_rating) #2568384 6086808

if __name__ == "__main__":
    main()
