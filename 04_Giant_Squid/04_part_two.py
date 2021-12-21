from __future__ import division
from assets import bingo_draw_numbers
from assets import bingo_board_numbers

test_draws = [7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1]

test_boards = [22, 13, 17, 11,  0,
 8,  2, 23,  4, 24,
21,  9, 14, 16,  7,
 6, 10,  3, 18,  5,
 1, 12, 20, 15, 19,
 3, 15,  0,  2, 22,
 9, 18, 13, 17,  5,
19,  8,  7, 25, 23,
20, 11, 10, 24,  4,
14, 21, 16, 12,  6,
14, 21, 17, 24,  4,
10, 16, 15,  9, 19,
18,  8, 23, 26, 20,
22, 11, 13,  6,  5,
 2,  0, 12,  3,  7]

def generate_bingo_boards(numbers):
    if (len(numbers) % 25 != 0):
        return 'Error - input string for the bingo boards needs to be a multiple of 25.'
    boards = []
    return [numbers[x:x+25] for x in xrange(0, len(numbers), 25)]

def draw_next_number(current_numbers, all_numbers):
    return [number for number in all_numbers[:len(current_numbers)+1]]

def is_horizontal_win(board, bingo_numbers):
    horizontal_partitions = [board[x:x+5] for x in xrange(0, len(board), 5)]
    for partition in horizontal_partitions:
        if all(numbers in bingo_numbers for numbers in partition): return True, partition
    return False, []

def is_vertical_win(board, bingo_numbers):
    vertical_partitions = []
    for i in range(len(board) // 5):
        partition = []
        for j in range(0,len(board),5):
            partition.append(board[i+j])
        vertical_partitions.append(partition)
    for partition in vertical_partitions:
        if(all(numbers in bingo_numbers for numbers in partition)): return True, partition
    return False, []

def is_win(boards, bingo_numbers):
    for board in boards:
        bingo, winning_numbers = is_horizontal_win(board, bingo_numbers)
        if bingo: return bingo, winning_numbers, board
        bingo, winning_numbers = is_vertical_win(board, bingo_numbers)
        if bingo: return bingo, winning_numbers, board
    return False, [], []


def find_sum_of_unmarked_numbers(board, picked_numbers):
    sum_board = sum(board)
    sum_marked_numbers = sum([number for number in board if number in picked_numbers])
    return sum_board - sum_marked_numbers

def simulate_bingo(boards, numbers):
    picked_numbers = numbers[:4]
    finished_boards = []
    not_finished_boards = boards
    no_bingo = True
    while len(picked_numbers) < len(numbers):
        if (no_bingo): 
            picked_numbers = draw_next_number(picked_numbers, numbers)
            no_bingo = True
        is_bingo, winning_numbers, winning_board = is_win(not_finished_boards, picked_numbers)
        if (is_bingo):
            finished_boards.append((winning_board, picked_numbers))
            not_finished_boards.remove(winning_board)
        no_bingo = not is_bingo

    return finished_boards[-1]


def main():
    bingo_boards = generate_bingo_boards(bingo_board_numbers) 
    result = simulate_bingo(bingo_boards, bingo_draw_numbers)
    sum_unmarked_numbers = find_sum_of_unmarked_numbers(result[0], result[1])
    print(sum_unmarked_numbers * result[1][-1])
    
if __name__ == "__main__":
    main()