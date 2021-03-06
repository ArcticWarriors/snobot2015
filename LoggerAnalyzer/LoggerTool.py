'''
Created on Jan 31, 2015

@author: 1335draco
'''

import numpy
import time
import os
import matplotlib.pyplot as plt
from read_csv_file import load_csv_file


def parse_datetime(time_string):

    # strptime has no option for milliseconds, so ignore the last 3 characters
    time_struct = time.strptime(time_string[:-3], "%Y%m%d_%H%M%S")

    hour = time_struct.tm_hour
    minute = time_struct.tm_min
    sec = time_struct.tm_sec
    msec = int(time_string[-3:])  # ms are the last three columns

    return (msec + (sec * 1000) + (minute * 60000) + (hour * 3600000)) / 1000.0


def parse_drivemode(value):

    if value == "Tank":
        return 0
    else:
        return 1


def plot_driver_joysticks(converted_dict):

    print converted_dict.keys()

    tank_right = converted_dict["Tank Mode: Right Y Axis"]
    tank_left = converted_dict["Tank Mode: Left Y Axis"]

    arcade_speed = converted_dict["Arcade Mode: Speed (1 to -1)"]
    arcade_rotate = converted_dict["Arcade Mode: Right X Axis"]

    x_axis = numpy.arange(len(tank_right))

    plt.subplot(4, 1, 1)
    plt.title('Driver Joysticks')
    plt.ylabel('Tank Right')
    plt.scatter(x_axis, tank_right, c=tank_right, marker="o", cmap=plt.get_cmap("gist_rainbow_r"), edgecolors='None')
    plt.colorbar()

    plt.subplot(4, 1, 2)
    plt.ylabel('Tank Left')
    plt.plot(tank_left, c='g', marker=".")

    plt.subplot(4, 1, 3)
    plt.ylabel('Arcade Speed')
    plt.plot(arcade_speed, c='r', marker="s")

    plt.subplot(4, 1, 4)
    plt.ylabel('Arcade Rotate')
    plt.plot(arcade_rotate, c='k', marker="D")


def plot_heading(converted_dict):

    curr_heading = converted_dict["Heading"]
    voltage = converted_dict["Voltage"]

    x_axis = numpy.arange(len(voltage))

    plt.title("Heading")
    plt.ylabel("Angle (Degrees)")
    plt.scatter(x_axis, curr_heading, c=voltage, marker="*", edgecolors='None')
    plt.colorbar()


def plot_voltage(converted_dict):

    voltage = converted_dict["Voltage"]
    dt = converted_dict["Date and Time"]

    x_axis = numpy.arange(len(dt))

    plt.title("Voltage")
    plt.ylabel("Volts")
    plt.scatter(x_axis, voltage, c=voltage, marker="*" , cmap=plt.get_cmap("RdYlGn"), edgecolors='None')
    plt.colorbar()


def plot_pressure(converted_dict):
    pressure = converted_dict["Claw Pressure"]
    dt = converted_dict["Date and Time"]

    x_axis = numpy.arange(len(dt))

    plt.title("Pressure")
    plt.ylabel("Pressure / Square In. (PSI)")
    plt.scatter(x_axis, pressure, c=pressure, marker="*", cmap=plt.get_cmap("RdYlGn"), edgecolors='None')
    plt.colorbar()


def plot_distanceTraveled(converted_dict):
    traveled = converted_dict["Traveled"]
    dt = converted_dict["Date and Time"]

    x_axis = numpy.arange(len(dt))

    plt.title("Distance Traveled Since Last Cycle")
    plt.ylabel("Units")
    plt.scatter(x_axis, traveled, c=traveled, marker="*", cmap=plt.get_cmap("RdYlGn"), edgecolors='None')

def plot_speed(converted_dict):

    leftSpeed = converted_dict["Left Drive Speed"]
    rightSpeed = converted_dict["Right Drive Speed"]

    dt = converted_dict["Date and Time"]

    x_axis = numpy.arange(len(dt))

    plt.title("Speed")

    plt.subplot(3,1,1)
    plt.ylabel("Left Speed")
    plt.scatter(x_axis, leftSpeed, c=leftSpeed, marker="*", cmap=plt.get_cmap("RdYlGn"), edgecolor="None")

    plt.subplot(3,1,2)
    plt.ylabel("Right Speed")
    plt.scatter(x_axis, rightSpeed, c=rightSpeed, marker="*", cmap=plt.get_cmap("RdYlGn"), edgecolor="None")


def plot_stacker(converted_dict):

    upper_limit = converted_dict["UpperLimitSwitchState"]
    lower_limit = converted_dict["LowerLimitSwitchState"]

    limit_switch_activated = numpy.logical_or(upper_limit, lower_limit)

    plt.subplot(3, 1, 1)
    plt.title('Stacker')
    plt.ylabel('Upper Limit Switch')
    plt.plot(upper_limit, c='b', marker="o")

    plt.subplot(3, 1, 2)
    plt.ylabel('Lower Limit Switch')
    plt.plot(lower_limit, c='b', marker="o")

    plt.subplot(3, 1, 3)
    plt.ylabel('A Switch Hit')
    plt.plot(limit_switch_activated, c='b', marker="o")


def plot_dt(converted_dict):

    the_time = converted_dict['Date and Time']
    dt = the_time[1:] - the_time[:-1]

    plt.title('delta time')
    plt.plot(dt, c='b', marker=".")


def main():
    file_name = "Team 174 Robot Logs - RobotLog_20150210_054531870_log.csv"
    converted_dict = load_csv_file(file_name, {'Date and Time': parse_datetime, 'Drive Mode': parse_drivemode})

    image_dir = "images/"
    if not os.path.exists(image_dir):
        os.mkdir(image_dir)

    plt.figure(1)
    plot_driver_joysticks(converted_dict)
    plt.savefig(image_dir + "DriverSticks.png")

    plt.figure(2)
    plot_stacker(converted_dict)
    plt.savefig(image_dir + "Stacker.png")

    plt.figure(3)
    plot_dt(converted_dict)
    plt.savefig(image_dir + "LoopLatency.png")

    plt.figure(4)
    plot_heading(converted_dict)
    plt.savefig(image_dir + "Heading.png")

    plt.figure(5)
    plot_voltage(converted_dict)
    plt.savefig(image_dir + "Voltage.png")

    plt.figure(6)
    plot_pressure(converted_dict)
    plt.savefig(image_dir + "Pressure.png")

    plt.figure(7)
    plot_distanceTraveled(converted_dict)
    plt.savefig(image_dir + "DistanceTraveled.png")

    plt.figure(8)
    plot_speed(converted_dict)
    plt.savefig(image_dir + "Speed.png")

    plt.show()


if __name__ == "__main__":
    main()
