import { Chart, registerables } from "chart.js";
import { useState, useEffect, useRef } from "react";


const Main = () => {
    const [orders, setOrders] = useState([]);
    const [begin, setBegin] = useState("");
    const [end, setEnd] = useState("");
    const [present, setPresent] = useState([])
    const [deleteStatus, setDeleteStatus] = useState(true);
    const [presentYear, setPresentYear] = useState('2023');
    const [presentRevenue, setPresentRevenue] = useState([])
    // const []
    const chartRef = useRef()
    //const [search,setSearch] = useState('');
    useEffect(() => {
        Chart.register(...registerables);
        var config = {
            type: "bar",
            data: {
                labels: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12'],
                datasets: [
                    {
                        label: "Monthly revenue",
                        data: presentRevenue
                    }
                ]
            },
            options: {
                plugins: {
                    legend: {
                        labels: {
                            color: 'white',
                            font: {
                                size: 20
                            },
                        }
                    }
                },
                scales: {
                    y: {
                        ticks: {
                            font: {
                                size: 20
                            },
                            color: 'white'
                        }
                    },
                    x: {
                        ticks: {
                            font: {
                                size: 20
                            },
                            color: 'white'
                        }
                    }
                }
            }

        }
        var revenueChart = new Chart(chartRef.current, config);
        return () => { revenueChart.destroy(); }
    }, [presentRevenue])

    // const chart = Chart.getChart('23');
    // chart.destroy();
    function findMostFrequentEmployee(orders) {
        let counts = {};
        let maxId = null;
        let maxCount = -1;
        orders.forEach((order) => {
          if (counts[order.id_employee]) {
            counts[order.id_employee]++;
          } else {
            counts[order.id_employee] = 1;
          }
        });
        for (let id in counts) {
          if (counts[id] > maxCount) {
            maxCount = counts[id];
            maxId = id;
          }
        }
        return maxId;
    }
    function findMostFrequentCus(orders) {
        let counts = {};
        let maxId = null;
        let maxCount = -1;
        orders.forEach((order) => {
          if (counts[order.id_customer]) {
            counts[order.id_customer]++;
          } else {
            counts[order.id_customer] = 1;
          }
        });
        for (let id in counts) {
          if (counts[id] > maxCount) {
            maxCount = counts[id];
            maxId = id;
          }
        }
        return maxId;
    }

    useEffect(() => {
        fetch("http://localhost:8080/orders")
            .then((res) => res.json())
            .then((data) => {
                setOrders(data); setPresent(data);
            })
            .catch((err) => console.log(err))

    }, []);
    var total = orders.reduce((acc, order) => acc + order.total_price, 0);
    var totalPresent = present.reduce((acc, order) => acc + order.total_price, 0);
    function filterOrdersByDate(beginDate, endDate) {
        let result = [];
        orders.forEach((order) => {
            let orderDate = new Date(order.order_date);
            if (orderDate > beginDate && orderDate < endDate) {
                result.push(order);
            }
        });
        return result;
    }
    // console.log(filterOrdersByDate(new Date('2023-03-01'), new Date('2023-05-14')));
    // console.log(begin);
    // console.log(end);
    function handleClear() {
        setBegin('');
        setEnd('');
        setPresent(orders);
    }
    function handleDelete(id) {
        fetch(`http://localhost:8080/delete/${id}`, {
            method: "DELETE",
        })
            .then((res) => res.json())
            .then((data) => {
                // if(data===false){
                //     alert('Chỉ được xóa cách giao dịch cách đây 1 năm!');
                // }else{
                //     window.location.reload(true);
                // }
                window.location.reload(true);
            })
            .catch((err) => console.log(err));
    }
    function handleChartData(data) {
        fetch(`http://localhost:8080/orders/${data}`)
            .then((res) => res.json())
            .then((data) => { setPresentRevenue(changeRevenueList(data)) })
            .catch((err) => console.log(err))
    }
    function changeRevenueList(data) {
        var result = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        data.forEach((monthRevenue) => {
            result[monthRevenue.month - 1] = monthRevenue.revenue;
        })
        return result;
    }

    return (
        <div style={{ 'margin': '50px' }}>
            <h2 className='text-center'>Cursory info</h2>
            <div className="row" style={{'display':"flex",'justifyContent':'center'}}>
                <div style={{'background-color':'#3466cd','width':'20%','margin':'20px','padding':'10px','border-radius':'10px'}}>
                    <h4>Total revenue</h4>
                    <h6>{total} VNĐ</h6>
                </div>
                <div style={{'background-color':'#9a0099','width':'20%','margin':'20px','padding':'10px','border-radius':'10px'}}>
                    <h4>Total orders</h4>
                    <h6>{orders.length}</h6>
                </div>
                <div style={{'background-color':'#0f961b','width':'20%','margin':'20px','padding':'10px','border-radius':'10px'}}>
                    <h4>Employee of the year</h4>
                    <h6>{findMostFrequentEmployee(orders)}</h6>
                </div>
                <div style={{'background-color':'#ff9901','width':'20%','margin':'20px','padding':'10px','border-radius':'10px'}}>
                    <h4>Potential Customer</h4>
                    <h6>{findMostFrequentCus(orders)}</h6>
                </div>
            </div>
            <h2 className='text-center'> Orders List</h2>
            <div className='row'>
                <input type="date" className="form-control" value={begin} onChange={e => setBegin(e.target.value)} />
                to
                <input type="date" className="form-control" value={end} onChange={e => setEnd(e.target.value)} />
                <button style={{ 'margin-top': '10px' }} className="btn btn-success" onClick={() => setPresent(filterOrdersByDate(new Date(begin), new Date(end)))}>Search</button>
                <button style={{ 'margin': '10px 0px' }} className="btn btn-danger" onClick={() => handleClear()}>Clear</button>
            </div>
            <div className='row'>
                <table className='table table-striped table-bordered'>
                    <thead>
                        <tr>
                            <th>Order_code</th>
                            <th>Order_date</th>
                            <th>Total_price</th>
                            <th>status</th>
                            <th>Note</th>
                            <th>Customer</th>
                            <th>Branch</th>
                            <th>Employee</th>
                            <th>Action</th>
                        </tr>
                    </thead>

                    <tbody>
                        {present.map((Order) => (
                            <tr key={Order.id_order}>
                                <td>{Order.order_code}</td>
                                <td>{Order.order_date}</td>
                                <td>{Order.total_price}</td>
                                <td>{Order.status}</td>
                                <td>{Order.note}</td>
                                <td>{Order.id_customer}</td>
                                <td>{Order.id_branch}</td>
                                <td>{Order.id_employee}</td>
                                <td>
                                    <button className='btn btn-danger' onClick={() => handleDelete(Order.id_order)}>Delete</button>
                                    {/* <button className='btn btn-danger'>Delete</button> */}
                                </td>
                            </tr>
                        ))}
                        <tr   >
                            <td colSpan="1">Tổng tiền</td>
                            <td align="center" colSpan='17'>{totalPresent}</td>
                        </tr>
                    </tbody>

                </table>
            </div>
            <h2 className='text-center'>Revenue in {presentYear}</h2>
            <div className='row' >
                <input type="text" className="form-control" value={presentYear} onChange={(e) => { setPresentYear(e.target.value) }} />
                <button style={{ 'margin-top': '10px' }} className="btn btn-success" onClick={() => handleChartData(presentYear)}>Search</button>
                <canvas ref={chartRef} id="canvas"></canvas>
            </div>

        </div>
    );
}
export default Main;